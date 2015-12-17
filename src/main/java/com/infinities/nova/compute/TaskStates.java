package com.infinities.nova.compute;

public class TaskStates {

	public final static String SCHEDULING = "scheduling";
	public final static String BLOCK_DEVICE_MAPPING = "block_device_mapping";
	public final static String NETWORKING = "networking";
	public final static String SPAWNING = "spawing";

	public final static String IMAGE_SNAPSHOT = "image_snapshot";
	public final static String IMAGE_SNAPSHOT_PENDING = "image_snapshot_pending";
	public final static String IMAGE_PENDING_UPLOAD = "image_pending_upload";
	public final static String IMAGE_UPLOADING = "image_uploading";
	public final static String IMAGE_BACKUP = "image_backup";

	public final static String UPDATING_PASSWORD = "updating_password";

	public final static String RESIZE_PREP = "resize_prep";
	public final static String RESIZE_MIGRATING = "resize_migrating";
	public final static String RESIZE_MIGRATED = "resize_migrated";
	public final static String RESIZE_FINISH = "resize_finish";

	public final static String RESIZE_REVERTING = "resize_reverting";
	public final static String RESIZE_CONFIRMING = "resize_confirming";

	public final static String REBOOTING = "rebooting";
	public final static String REBOOT_PENDING = "reboot_pending";
	public final static String REBOOT_STARTED = "reboot_started";
	public final static String REBOOTING_HARD = "rebooting_hard";
	public final static String REBOOT_PENDING_HARD = "reboot_pending_hard";
	public final static String REBOOT_STARTED_HARD = "reboot_started_hard";

	public final static String PAUSING = "pausing";
	public final static String UNPAUSING = "unpausing";
	public final static String SUSPENDING = "suspending";
	public final static String RESUMING = "resuming";
	public final static String POWERING_OFF = "powering-off";
	public final static String POWERING_ON = "powering-on";
	public final static String RESCUING = "rescuing";
	public final static String UNRESCUING = "unrescuing";
	public final static String REBUILDING = "rebuilding";
	public final static String REBUILD_BLOCK_DEVICE_MAPPING = "rebuild_block_device_mapping";
	public final static String REBUILD_SPAWNING = "rebuild_spawning";
	public final static String MIGRATING = "migrating";
	public final static String DELETING = "deleting";
	public final static String SOFT_DELETING = "soft-deleting";
	public final static String RESTORING = "restoring";
	public final static String SHELVING = "shelving";
	public final static String SHELVING_IMAGE_PENDING_UPLOAD = "shelving_image_pending_upload";
	public final static String SHELVING_IMAGE_UPLOADING = "shelving_image_uploading";
	public final static String SHELVING_OFFLOADING = "shelving_offloading";
	public final static String UNSHELVING = "unshelving";

}
