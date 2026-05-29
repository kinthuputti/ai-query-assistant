package com.example.demo.rag;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RagService {

    private final ChatClient.Builder chatClientBuilder;
    private final VectorStore vectorStore;

    public String askQuestion(String query) {
        // 1. Embed query and retrieve top-5 semantically similar chunks
        List<Document> relevant = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(query)
                        .topK(5)
                        .similarityThreshold(0.65)
                        .build()
        );

        if (relevant.isEmpty()) {
            return "I don't have enough information in the uploaded documents to answer that.";
        }

        String context = relevant.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n\n---\n\n"));

        // 2. Build RAG prompt with retrieved context only
        String prompt = """
                You are a helpful assistant. Answer the question using ONLY the context below.
                If the answer is not in the context, say "I don't have enough information to answer that."
                Do not make up information.

                Context:
                %s

                Question: %s
                """.formatted(context, query);

        // 3. Call Ollama
        return chatClientBuilder.build()
                .prompt()
                .user(prompt)
                .call()
                .content();
    }
}