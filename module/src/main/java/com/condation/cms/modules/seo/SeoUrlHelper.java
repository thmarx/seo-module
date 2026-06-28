package com.condation.cms.modules.seo;

/*-
 * #%L
 * seo-module
 * %%
 * Copyright (C) 2024 - 2026 CondationCMS
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import com.condation.cms.api.SiteProperties;
import com.condation.cms.api.db.ContentNode;

final class SeoUrlHelper {

	private SeoUrlHelper () {
	}

	static String createUrl (final SiteProperties siteProperties, final ContentNode node) {
		return createUrl(siteProperties, patchUri(node.uri()));
	}

	static String createUrl (final SiteProperties siteProperties, final String uri) {
		return "%s/%s".formatted(baseUrl(siteProperties), normalizeUri(uri));
	}

	private static String baseUrl (final SiteProperties siteProperties) {
		String baseUrl = (String) siteProperties.get("baseurl");
		if (baseUrl == null) {
			baseUrl = "";
		}
		if (baseUrl.endsWith("/")) {
			baseUrl = baseUrl.substring(0, baseUrl.lastIndexOf("/"));
		}

		return baseUrl;
	}

	private static String normalizeUri (String uri) {
		if (uri.startsWith("/")) {
			uri = uri.substring(1);
		}
		return uri;
	}

	private static String patchUri (String uri) {
		if (uri.endsWith("index.md")) {
			uri = uri.replace("index.md", "");
		}
		if (uri.endsWith("/")) {
			uri = uri.substring(0, uri.lastIndexOf("/"));
		}
		if (uri.endsWith(".md")) {
			uri = uri.substring(0, uri.lastIndexOf("."));
		}
		return normalizeUri(uri);
	}
}
