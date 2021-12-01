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

package com.github.retrooper.packetevents.wrapper.play.server;

import com.github.retrooper.packetevents.event.impl.PacketSendEvent;
import com.github.retrooper.packetevents.manager.server.ServerVersion;
import com.github.retrooper.packetevents.protocol.chat.component.BaseComponent;
import com.github.retrooper.packetevents.protocol.chat.component.serializer.ComponentSerializer;
import com.github.retrooper.packetevents.protocol.chat.component.impl.TextComponent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.PacketWrapper;

import java.util.List;

public class WrapperPlayServerPlayerListHeaderAndFooter extends PacketWrapper<WrapperPlayServerPlayerListHeaderAndFooter> {
    public static boolean HANDLE_JSON = true;
    private static final int MODERN_MESSAGE_LENGTH = 262144;
    private static final int LEGACY_MESSAGE_LENGTH = 32767;
    private String headerJson;
    private String footerJson;
    private BaseComponent headerComponent;
    private BaseComponent footerComponent;
    public WrapperPlayServerPlayerListHeaderAndFooter(PacketSendEvent event) {
        super(event);
    }

    public WrapperPlayServerPlayerListHeaderAndFooter(BaseComponent headerComponent, BaseComponent footerComponent) {
        super(PacketType.Play.Server.PLAYER_LIST_HEADER_AND_FOOTER);
        this.headerComponent = headerComponent;
        this.footerComponent = footerComponent;
    }

    public WrapperPlayServerPlayerListHeaderAndFooter(String headerJson, String footerJson) {
        super(PacketType.Play.Server.PLAYER_LIST_HEADER_AND_FOOTER);
        this.headerJson = headerJson;
        this.footerJson = footerJson;
    }

    @Override
    public void readData() {
        int maxMessageLength = serverVersion.isNewerThanOrEquals(ServerVersion.V_1_13) ? MODERN_MESSAGE_LENGTH : LEGACY_MESSAGE_LENGTH;
        headerJson = readString(maxMessageLength);
        footerJson = readString(maxMessageLength);
        if (HANDLE_JSON) {
            headerComponent = ComponentSerializer.parseJsonComponent(headerJson);
            footerComponent = ComponentSerializer.parseJsonComponent(footerJson);
        }
    }

    @Override
    public void readData(WrapperPlayServerPlayerListHeaderAndFooter wrapper) {
        headerJson = wrapper.headerJson;
        footerJson = wrapper.footerJson;
        headerComponent = wrapper.headerComponent;
        footerComponent = wrapper.footerComponent;
    }

    @Override
    public void writeData() {
        int maxMessageLength = serverVersion.isNewerThanOrEquals(ServerVersion.V_1_13) ? MODERN_MESSAGE_LENGTH : LEGACY_MESSAGE_LENGTH;
        if (HANDLE_JSON) {
            headerJson = ComponentSerializer.buildJsonObject(headerComponent).toString();
            footerJson = ComponentSerializer.buildJsonObject(footerComponent).toString();
        }
        writeString(headerJson, maxMessageLength);
        writeString(footerJson, maxMessageLength);
    }

    public String getHeaderJson() {
        return headerJson;
    }

    public void setHeaderJson(String headerJson) {
        this.headerJson = headerJson;
    }

    public String getFooterJson() {
        return footerJson;
    }

    public void setFooterJson(String footerJson) {
        this.footerJson = footerJson;
    }

    public BaseComponent getHeaderComponent() {
        return headerComponent;
    }

    public void setHeaderComponent(BaseComponent headerComponent) {
        this.headerComponent = headerComponent;
    }

    public BaseComponent getFooterComponent() {
        return footerComponent;
    }

    public void setFooterComponent(BaseComponent footerComponent) {
        this.footerComponent = footerComponent;
    }
}
