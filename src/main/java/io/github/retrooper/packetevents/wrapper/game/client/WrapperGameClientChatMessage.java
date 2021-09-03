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

package io.github.retrooper.packetevents.wrapper.game.client;

import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.manager.player.ClientVersion;
import io.github.retrooper.packetevents.protocol.PacketType;
import io.github.retrooper.packetevents.utils.StringUtil;
import io.github.retrooper.packetevents.wrapper.PacketWrapper;
import io.github.retrooper.packetevents.wrapper.SendablePacketWrapper;

/**
 * This packet is used to send a chat message to the server.
 */
public class WrapperGameClientChatMessage extends SendablePacketWrapper<WrapperGameClientChatMessage> {
    private String message;

    public WrapperGameClientChatMessage(PacketReceiveEvent event) {
        super(event);
    }

    public WrapperGameClientChatMessage(ClientVersion clientVersion, String message) {
        super(PacketType.Game.Client.CHAT_MESSAGE.getPacketID(clientVersion), clientVersion);
        this.message = message;
    }

    @Override
    public void readData() {
        int maxMessageLength = clientVersion.isNewerThanOrEquals(ClientVersion.v_1_11) ? 256 : 100;
        this.message = readString(maxMessageLength);
    }

    @Override
    public void readData(WrapperGameClientChatMessage wrapper) {
        this.message = wrapper.message;
    }

    @Override
    public void writeData() {
        int maxMessageLength = clientVersion.isNewerThanOrEquals(ClientVersion.v_1_11) ? 256 : 100;
        this.message = StringUtil.maximizeLength(this.message, maxMessageLength);
        writeString(this.message, maxMessageLength);
    }

    /**
     * The message.
     * On {@link ClientVersion#v_1_10} and older clients, the message should never exceed 100 characters.
     * On {@link ClientVersion#v_1_11} and newer clients, the message should never exceed 256 characters.
     *
     * @return Message
     */
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
       this.message = message;
    }
}