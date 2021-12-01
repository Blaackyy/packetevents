/*
 * This file is part of packetevents - https://github.com/retrooper/packetevents
 * Copyright (C) 2021 retrooper and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.retrooper.packetevents.protocol.chat.component.impl;

import com.github.retrooper.packetevents.protocol.chat.Color;
import com.github.retrooper.packetevents.protocol.chat.component.BaseComponent;
import com.github.retrooper.packetevents.protocol.chat.component.ClickEvent;
import com.github.retrooper.packetevents.protocol.chat.component.HoverEvent;
import com.google.gson.JsonObject;

public class TextComponent extends BaseComponent {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void parseJson(JsonObject jsonObject) {
        if (jsonObject.has("text")) {
            text = jsonObject.get("text").getAsString();
        } else {
            text = "";
        }
        super.parseJson(jsonObject);
    }

    @Override
    public JsonObject buildJson() {
        JsonObject jsonObject = super.buildJson();
        jsonObject.addProperty("text", text);
        return jsonObject;
    }

    public static TextComponent.Builder builder() {
        return new Builder();
    }

    public static class Builder extends BaseComponent.Builder<Builder> {
        public Builder() {
            super(new TextComponent());
        }

        public Builder text(String text) {
            ((TextComponent)component).setText(text);
            return this;
        }

        public TextComponent build() {
            return (TextComponent) component;
        }
    }
}
