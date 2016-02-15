///*******************************************************************************
// * Copyright 2015 InfinitiesSoft Solutions Inc.
// *
// * Licensed under the Apache License, Version 2.0 (the "License"); you may
// * not use this file except in compliance with the License. You may obtain
// * a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
// * License for the specific language governing permissions and limitations
// * under the License.
// *******************************************************************************/
//package com.infinities.nova.network.model;
//
//import java.net.UnknownHostException;
//
//import com.google.common.base.Strings;
//import com.infinities.nova.util.CIDRUtils;
//
//public class IP extends Model {
//
//	private String address;
//	private String type;
//	private String version;
//	private String macAddress;
//
//
//	public String getAddress() {
//		return address;
//	}
//
//	public void setAddress(String address) {
//		this.address = address;
//	}
//
//	public String getType() {
//		return type;
//	}
//
//	public void setType(String type) {
//		this.type = type;
//	}
//
//	public String getVersion() {
//		return version;
//	}
//
//	public void setVersion(String version) {
//		this.version = version;
//	}
//
//	public String getMacAddress() {
//		return macAddress;
//	}
//
//	public void setMacAddress(String macAddress) {
//		this.macAddress = macAddress;
//	}
//
//	public boolean isInSubnet(com.infinities.nova.network.model.Subnet subnet) throws UnknownHostException {
//		if (!Strings.isNullOrEmpty(getAddress()) && !Strings.isNullOrEmpty(subnet.getCidr())) {
//			return new CIDRUtils(subnet.getCidr()).isInRange(getAddress());
//		}
//		return false;
//	}
//
// }
