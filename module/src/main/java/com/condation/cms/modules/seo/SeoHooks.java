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
import com.condation.cms.api.annotations.Action;
import com.condation.cms.api.db.ContentNode;
import com.condation.cms.api.extensions.HookSystemRegisterExtensionPoint;
import com.condation.cms.api.feature.features.CurrentNodeFeature;
import com.condation.cms.api.feature.features.SitePropertiesFeature;
import com.condation.modules.api.annotation.Extension;

/**
 *
 * @author thorstenmarx
 */
@Extension(HookSystemRegisterExtensionPoint.class)
public class SeoHooks extends HookSystemRegisterExtensionPoint {

	@Action("system/layout/html/header")
	public String meta () {
		if (!getRequestContext().has(CurrentNodeFeature.class)) {
			return "";
		}
		ContentNode node = getRequestContext().get(CurrentNodeFeature.class).node();
		SiteProperties siteProperties = getRequestContext().get(SitePropertiesFeature.class).siteProperties();

		StringBuilder sb = new StringBuilder();

		if (!node.getMetaValue("seo.index", true)) {
			sb.append("<meta name=\"robots\" content=\"noindex,nofollow\" />\n");
		}

		appendOpenGraph(sb, node, siteProperties);

		return sb.toString();
	}

	private void appendOpenGraph (StringBuilder sb, ContentNode node, SiteProperties siteProperties) {
		String title = node.getMetaValue("seo.og.title", node.getMetaValue("title", ""));
		String description = node.getMetaValue("seo.og.description", node.getMetaValue("excerpt", ""));
		String image = node.getMetaValue("seo.og.image", "");
		String type = node.getMetaValue("seo.og.type", "website");
		String url = node.getMetaValue("seo.og.url", SeoUrlHelper.createUrl(siteProperties, node));

		if (!title.isBlank()) {
			sb.append("<meta property=\"og:title\" content=\"").append(escapeHtml(title)).append("\" />\n");
		}
		if (!description.isBlank()) {
			sb.append("<meta property=\"og:description\" content=\"").append(escapeHtml(description)).append("\" />\n");
		}
		if (!image.isBlank()) {
			sb.append("<meta property=\"og:image\" content=\"").append(escapeHtml(image)).append("\" />\n");
		}
		sb.append("<meta property=\"og:type\" content=\"").append(escapeHtml(type)).append("\" />\n");
		sb.append("<meta property=\"og:url\" content=\"").append(escapeHtml(url)).append("\" />\n");
	}

	private String escapeHtml (String value) {
		return value
				.replace("&", "&amp;")
				.replace("\"", "&quot;")
				.replace("<", "&lt;")
				.replace(">", "&gt;");
	}
}
