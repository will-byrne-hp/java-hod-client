package com.hp.autonomy.hod.client.api.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hp.autonomy.hod.client.converter.DoNotConvert;
import lombok.Data;

import java.util.regex.Pattern;

/**
 * Represents a domain-qualified resource identifier; for example the identifier for a text index or user store.
 */
@Data
@DoNotConvert
public class ResourceIdentifier {

    public static final String PUBLIC_INDEXES_DOMAIN = "PUBLIC_INDEXES";

    public static final ResourceIdentifier WIKI_CHI = new ResourceIdentifier(PUBLIC_INDEXES_DOMAIN, "wiki_chi");
    public static final ResourceIdentifier WIKI_ENG = new ResourceIdentifier(PUBLIC_INDEXES_DOMAIN, "wiki_eng");
    public static final ResourceIdentifier WIKI_FRA = new ResourceIdentifier(PUBLIC_INDEXES_DOMAIN, "wiki_fra");
    public static final ResourceIdentifier WIKI_GER = new ResourceIdentifier(PUBLIC_INDEXES_DOMAIN, "wiki_ger");
    public static final ResourceIdentifier WIKI_ITA = new ResourceIdentifier(PUBLIC_INDEXES_DOMAIN, "wiki_ita");
    public static final ResourceIdentifier WIKI_SPA = new ResourceIdentifier(PUBLIC_INDEXES_DOMAIN, "wiki_spa");
    public static final ResourceIdentifier WORLD_FACTBOOK = new ResourceIdentifier(PUBLIC_INDEXES_DOMAIN, "world_factbook");
    public static final ResourceIdentifier NEWS_ENG = new ResourceIdentifier(PUBLIC_INDEXES_DOMAIN, "news_eng");
    public static final ResourceIdentifier NEWS_FRA = new ResourceIdentifier(PUBLIC_INDEXES_DOMAIN, "news_fra");
    public static final ResourceIdentifier NEWS_GER = new ResourceIdentifier(PUBLIC_INDEXES_DOMAIN, "news_ger");
    public static final ResourceIdentifier NEWS_ITA = new ResourceIdentifier(PUBLIC_INDEXES_DOMAIN, "news_ita");
    public static final ResourceIdentifier ARXIV = new ResourceIdentifier(PUBLIC_INDEXES_DOMAIN, "arxiv");
    public static final ResourceIdentifier PATENTS = new ResourceIdentifier(PUBLIC_INDEXES_DOMAIN, "patents");

    private static final String SEPARATOR = ":";
    private static final Pattern ESCAPE_PATTERN = Pattern.compile("([\\\\:])");

    private final String domain;
    private final String name;

    public ResourceIdentifier(
            @JsonProperty("domain") final String domain,
            @JsonProperty("name") final String name
    ) {
        this.domain = domain;
        this.name = name;
    }

    /**
     * Escapes the domain and name and joins them with a colon.
     * @return The HOD resource identifier string
     */
    @Override
    public String toString() {
        return escapeComponent(domain) + SEPARATOR + escapeComponent(name);
    }

    /**
     * HOD resource names (text index names, domain names etc) must have : escaped to \: and \ escaped to \\ when
     * combined into a resource identifier.
     * @param input The string to escape
     * @return The escaped string
     */
    private static String escapeComponent(final String input) {
        return ESCAPE_PATTERN.matcher(input).replaceAll("\\\\$1");
    }
}
