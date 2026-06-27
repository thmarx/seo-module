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

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import com.condation.cms.api.SiteProperties;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RobotsTxtGenerator implements AutoCloseable {
    private final OutputStream output;
    private final SiteProperties siteProperties;

    public void create() throws Exception {
        output.write("Sitemap: %s\r\n".formatted(createURL("sitemap.xml")).getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void close() throws Exception {
        output.close();
    }

    private String createURL (final String uri) {
		String baseUrl = (String) siteProperties.get("baseurl");
		if (baseUrl == null) {
			baseUrl = "";
		}
		if (baseUrl.endsWith("/")) {
			baseUrl = baseUrl.substring(0, baseUrl.lastIndexOf("/"));
		}
		
		return "%s/%s".formatted(baseUrl, uri);
	}
}
