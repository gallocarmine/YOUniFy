package it.younify.modules.sgtr.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.ahocorasick.trie.Emit;
import org.ahocorasick.trie.Trie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * The {@code CensorshipService} is responsible for detecting and censoring sensitive or inappropriate content.
 * It uses an Aho-Corasick trie for efficient, simultaneous matching of multiple keywords.
 * The list of "bad words" is injected from the application's configuration.
 *
 * @see org.ahocorasick.trie.Trie
 */
@Service
@RequiredArgsConstructor
public class CensorshipService {

    private volatile Trie trie;

    @Value("${app.censorship.bad-words:#{T(java.util.Collections).emptyList()}}")
    private List<String> badWords;

    /**
     * Initializes or refreshes the censorship trie.
     * This method is called on startup and can be invoked to reload the keyword list.
     * It builds a case-insensitive, whole-words-only trie from the configured bad words list.
     */
    @PostConstruct
    public void refresh() {

        Trie.TrieBuilder builder =
                Trie.builder()
                    .ignoreCase()
                    .onlyWholeWords();

        for (String word : badWords) {

            builder.addKeyword(word);
        }

        this.trie = builder.build();
    }


    /**
     * Checks if the given text contains any of the configured sensitive words.
     *
     * @param text The text to check.
     * @return {@code true} if a match is found, {@code false} otherwise.
     */
    public boolean containsSensitiveContent(String text) {

        if (text == null || text.isEmpty()) return false;

        return trie.containsMatch(text);
    }


    /**
     * Censors any sensitive words found in the given text.
     * It replaces each matched word with a predefined placeholder.
     *
     * @param text The text to censor.
     * @return The censored version of the text.
     */
    public String censorText(String text) {

        Collection<Emit> emits = trie.parseText(text);

        List<Emit> emitList = new ArrayList<>(emits);
        emitList.sort(Comparator.comparing(Emit::getStart).reversed());

        StringBuilder sb = new StringBuilder(text);

        for (Emit emit : emitList) {

            int start = emit.getStart();
            int end = emit.getEnd() + 1;

            String replacement = "SIG.RA ROSINA";
            sb.replace(start, end, replacement);
        }

        return sb.toString();
    }
}
