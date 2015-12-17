package com.infinities.nova.response.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public interface ServerAction extends Serializable {

	@XmlRootElement(name = "changePassword")
	public static final class ChangePassword implements ServerAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String adminPass;


		public ChangePassword() {
			super();
		}

		public ChangePassword(String adminPass) {
			this.adminPass = adminPass;
		}

		/**
		 * @return the adminPass
		 */
		public String getAdminPass() {
			return adminPass;
		}

		/**
		 * @param adminPass
		 *            the adminPass to set
		 */
		public void setAdminPass(String adminPass) {
			this.adminPass = adminPass;
		}

	}

	@XmlRootElement(name = "reboot")
	public static final class Reboot implements ServerAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String type;


		/**
		 * @return the type
		 */
		public String getType() {
			return type;
		}

		/**
		 * @param type
		 *            the type to set
		 */
		public void setType(String type) {
			this.type = type;
		}

	}

	@XmlRootElement(name = "rebuild")
	public static final class Rebuild implements ServerAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private String imageRef;

		private String name;

		private String adminPass;

		private String accessIPv4;

		private String accessIPv6;

		private Map<String, String> metadata = new HashMap<String, String>();

		private List<PersonalityFile> personality = new ArrayList<PersonalityFile>();

		@XmlElement(name = "OS-DCF:diskConfig")
		private String diskConfig;


		/**
		 * @return the imageRef
		 */
		public String getImageRef() {
			return imageRef;
		}

		/**
		 * @param imageRef
		 *            the imageRef to set
		 */
		public void setImageRef(String imageRef) {
			this.imageRef = imageRef;
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name
		 *            the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the adminPass
		 */
		public String getAdminPass() {
			return adminPass;
		}

		/**
		 * @param adminPass
		 *            the adminPass to set
		 */
		public void setAdminPass(String adminPass) {
			this.adminPass = adminPass;
		}

		/**
		 * @return the accessIPv4
		 */
		public String getAccessIPv4() {
			return accessIPv4;
		}

		/**
		 * @param accessIPv4
		 *            the accessIPv4 to set
		 */
		public void setAccessIPv4(String accessIPv4) {
			this.accessIPv4 = accessIPv4;
		}

		/**
		 * @return the accessIPv6
		 */
		public String getAccessIPv6() {
			return accessIPv6;
		}

		/**
		 * @param accessIPv6
		 *            the accessIPv6 to set
		 */
		public void setAccessIPv6(String accessIPv6) {
			this.accessIPv6 = accessIPv6;
		}

		/**
		 * @return the metadata
		 */
		public Map<String, String> getMetadata() {
			return metadata;
		}

		/**
		 * @param metadata
		 *            the metadata to set
		 */
		public void setMetadata(Map<String, String> metadata) {
			this.metadata = metadata;
		}

		/**
		 * @return the personality
		 */
		public List<PersonalityFile> getPersonality() {
			return personality;
		}

		/**
		 * @param personality
		 *            the personality to set
		 */
		public void setPersonality(List<PersonalityFile> personality) {
			this.personality = personality;
		}

		/**
		 * @return the diskConfig
		 */
		public String getDiskConfig() {
			return diskConfig;
		}

		/**
		 * @param diskConfig
		 *            the diskConfig to set
		 */
		public void setDiskConfig(String diskConfig) {
			this.diskConfig = diskConfig;
		}

	}

	@XmlRootElement(name = "resize")
	public static final class Resize implements ServerAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private String flavorRef;

		@XmlElement(name = "OS-DCF:diskConfig")
		private String diskConfig;


		/**
		 * @return the flavorRef
		 */
		public String getFlavorRef() {
			return flavorRef;
		}

		/**
		 * @param flavorRef
		 *            the flavorRef to set
		 */
		public void setFlavorRef(String flavorRef) {
			this.flavorRef = flavorRef;
		}

		/**
		 * @return the diskConfig
		 */
		public String getDiskConfig() {
			return diskConfig;
		}

		/**
		 * @param diskConfig
		 *            the diskConfig to set
		 */
		public void setDiskConfig(String diskConfig) {
			this.diskConfig = diskConfig;
		}

	}

	@XmlRootElement(name = "confirmResize")
	public static final class ConfirmResize implements ServerAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	}

	@XmlRootElement(name = "revertResize")
	public static final class RevertResize implements ServerAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	}

	@XmlRootElement(name = "createImage")
	public static final class CreateImage implements ServerAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private String name;

		private Map<String, String> metadata;


		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name
		 *            the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the metadata
		 */
		public Map<String, String> getMetadata() {
			return metadata;
		}

		/**
		 * @param metadata
		 *            the metadata to set
		 */
		public void setMetadata(Map<String, String> metadata) {
			this.metadata = metadata;
		}

	}

	@XmlRootElement(name = "rescue")
	public static final class Rescue implements ServerAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String adminPass;


		public Rescue() {

		}

		public Rescue(String adminPass) {
			this.adminPass = adminPass;
		}

		/**
		 * @return the adminPass
		 */
		public String getAdminPass() {
			return adminPass;
		}

		/**
		 * @param adminPass
		 *            the adminPass to set
		 */
		public void setAdminPass(String adminPass) {
			this.adminPass = adminPass;
		}

	}

	public static final class RescueResponse implements ServerAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String adminPass;


		/**
		 * @return the adminPass
		 */
		public String getAdminPass() {
			return adminPass;
		}

	}

	@XmlRootElement(name = "unrescue")
	public static final class Unrescue implements ServerAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	}

	@XmlRootElement(name = "unpause")
	public static final class Unpause implements ServerAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	}

	@XmlRootElement(name = "pause")
	public static final class Pause implements ServerAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	}

	@XmlRootElement(name = "suspend")
	public static final class Suspend implements ServerAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	}

	@XmlRootElement(name = "resume")
	public static final class Resume implements ServerAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	}

	@XmlRootElement(name = "lock")
	public static final class Lock implements ServerAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	}

	@XmlRootElement(name = "unlock")
	public static final class Unlock implements ServerAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	}

	@XmlRootElement(name = "os-getConsoleOutput")
	public static final class GetConsoleOutput implements ServerAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Integer length;


		public GetConsoleOutput() {

		}

		public GetConsoleOutput(Integer length) {
			this.length = length;
		}

		/**
		 * @return the length
		 */
		public Integer getLength() {
			return length;
		}

		/**
		 * @param length
		 *            the length to set
		 */
		public void setLength(Integer length) {
			this.length = length;
		}

	}

	public static final class ConsoleOutput implements ServerAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String output;


		/**
		 * @return the output
		 */
		public String getOutput() {
			return output;
		}

	}

	@XmlRootElement(name = "os-getVNCConsole")
	public static final class GetVncConsole implements ServerAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String type;


		public GetVncConsole() {
			super();
		}

		public GetVncConsole(String type) {
			super();
			this.type = type;
		}

		/**
		 * @return the type
		 */
		public String getType() {
			return type;
		}

		/**
		 * @param type
		 *            the type to set
		 */
		public void setType(String type) {
			this.type = type;
		}

	}

	@XmlRootElement(name = "console")
	public static final class VncConsole implements ServerAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private String type;

		private String url;


		/**
		 * @return the type
		 */
		public String getType() {
			return type;
		}

		/**
		 * @return the url
		 */
		public String getUrl() {
			return url;
		}

	}

	@XmlRootElement(name = "os-start")
	public static final class Start implements ServerAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	}

	@XmlRootElement(name = "os-stop")
	public static final class Stop implements ServerAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	}

	@XmlRootElement(name = "forceDelete")
	public static final class ForceDelete implements ServerAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	}

	@XmlRootElement(name = "restore")
	public static final class Restore implements ServerAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	}

	@XmlRootElement(name = "addFloatingIp")
	public static final class AssociateFloatingIp implements ServerAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String address;


		public AssociateFloatingIp() {
			super();
		}

		public AssociateFloatingIp(String address) {
			super();
			this.address = address;
		}

		/**
		 * @return the address
		 */
		public String getAddress() {
			return address;
		}

		/**
		 * @param address
		 *            the address to set
		 */
		public void setAddress(String address) {
			this.address = address;
		}

	}

	@XmlRootElement(name = "removeFloatingIp")
	public static final class DisassociateFloatingIp implements ServerAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String address;


		public DisassociateFloatingIp() {
			super();
		}

		public DisassociateFloatingIp(String address) {
			super();
			this.address = address;
		}

		/**
		 * @return the address
		 */
		public String getAddress() {
			return address;
		}

		/**
		 * @param address
		 *            the address to set
		 */
		public void setAddress(String address) {
			this.address = address;
		}

	}

	@XmlRootElement(name = "createBackup")
	public static final class CreateBackup implements ServerAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private String name;

		@XmlElement(name = "backup_type")
		private String type;

		private String rotation;

		private Map<String, String> metadata;


		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * @param name
		 *            the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * @return the type
		 */
		public String getType() {
			return type;
		}

		/**
		 * @param type
		 *            the type to set
		 */
		public void setType(String type) {
			this.type = type;
		}

		/**
		 * @return the rotation
		 */
		public String getRotation() {
			return rotation;
		}

		/**
		 * @param rotation
		 *            the rotation to set
		 */
		public void setRotation(String rotation) {
			this.rotation = rotation;
		}

		/**
		 * @return the metadata
		 */
		public Map<String, String> getMetadata() {
			return metadata;
		}

		/**
		 * @param metadata
		 *            the metadata to set
		 */
		public void setMetadata(Map<String, String> metadata) {
			this.metadata = metadata;
		}

	}

}
