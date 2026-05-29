package com.example.demo.service;

import com.example.demo.entity.DocumentEntity;
import com.example.demo.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private final DocumentRepository documentRepository;
    private final VectorStore vectorStore;

    public String uploadFile(MultipartFile file) throws IOException {
        String content = new String(file.getBytes(), StandardCharsets.UTF_8);

        // 1. Persist raw content to Postgres (unchanged)
        DocumentEntity document = DocumentEntity.builder()
                .fileName(file.getOriginalFilename())
                .content(content)
                .build();
        documentRepository.save(document);

        // 2. Chunk into smaller pieces and embed into PgVector
        Document doc = new Document(content);
        TokenTextSplitter splitter = new TokenTextSplitter(500, 100, 5, 10000, true);
        List<Document> chunks = splitter.apply(List.of(doc));
        vectorStore.add(chunks);

        return "Uploaded and indexed: " + file.getOriginalFilename()
                + " (" + chunks.size() + " chunks stored in vector store)";
    }
}