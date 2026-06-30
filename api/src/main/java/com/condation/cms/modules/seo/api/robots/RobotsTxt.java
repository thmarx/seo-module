
package com.condation.cms.modules.seo.api.robots;

/*-
 * #%L
 * seo-api
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

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public final class RobotsTxt {

    private final List<RobotsGroup> groups = new ArrayList<>();
    private final List<String> sitemaps = new ArrayList<>();
    private final List<String> comments = new ArrayList<>();

    public void comment (String comment) {
        comments.add(comment);
    }
    
    public RobotsGroup group(String userAgent) {
        return groups.stream()
                .filter(group -> group.userAgent().equalsIgnoreCase(userAgent))
                .findFirst()
                .orElseGet(() -> {
                    var group = new RobotsGroup(userAgent);
                    groups.add(group);
                    return group;
                });
    }

    public void addSitemap(String sitemap) {
        sitemaps.add(sitemap);
    }
    
    private List<RobotsGroup> groups() {
        return groups;
    }

    private List<String> sitemaps() {
        return sitemaps;
    }

    private List<String> comments() {
        return comments;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();

        for (String comment : comments) {
            if (comment == null || comment.isBlank()) {
                continue;
            }

            String normalized = comment.startsWith("#")
                    ? comment
                    : "# " + comment;

            output.append(normalized).append('\n');
        }

        if (!comments.isEmpty() && (!groups.isEmpty() || !sitemaps.isEmpty())) {
            output.append('\n');
        }

        for (int i = 0; i < groups.size(); i++) {
            RobotsGroup group = groups.get(i);

            if (group == null || group.isEmpty()) {
                continue;
            }

            output.append(group);

            if (i < groups.size() - 1) {
                output.append('\n');
            }
        }

        if (!groups.isEmpty() && !sitemaps.isEmpty()) {
            output.append('\n');
        }

        for (String sitemap : sitemaps) {
            if (sitemap == null || sitemap.isBlank()) {
                continue;
            }

            output.append("Sitemap: ")
                    .append(sitemap.trim())
                    .append('\n');
        }

        return output.toString();
    }
}
