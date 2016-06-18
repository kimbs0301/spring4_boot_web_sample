package com.example.spring.logic.common.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.boot.context.embedded.MimeMappings.Mapping;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;

/**
 * @author gimbyeongsu
 * 
 */
public class MimeUtils {
	private static final Set<String> MIME_TYPE_SET = new HashSet<>();

	private MimeUtils() {

	}

	static {
		for (Mapping each : MimeMappings.DEFAULT.getAll()) {
			MIME_TYPE_SET.add(each.getMimeType());
		}
	}

	public static List<String> getMimeList(String s) {
		List<String> result = new ArrayList<>();
		List<String> mimeList = Splitter.on(CharMatcher.anyOf(",;)")).trimResults().omitEmptyStrings().splitToList(s);
		for (String mime : mimeList) {
			if (MIME_TYPE_SET.contains(mime)) {
				result.add(mime);
			}
		}
		return result;
	}

	public static Set<String> getMimeSet(String s) {
		Set<String> result = new HashSet<>();
		List<String> mimeList = Splitter.on(CharMatcher.anyOf(",;)")).trimResults().omitEmptyStrings().splitToList(s);
		for (String mime : mimeList) {
			if (MIME_TYPE_SET.contains(mime)) {
				result.add(mime);
			}
		}
		return result;
	}
}
