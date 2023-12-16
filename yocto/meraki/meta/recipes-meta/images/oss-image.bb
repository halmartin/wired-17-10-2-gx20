SUMMARY = "A small image just capable of allowing a device to boot."
IMAGE_INSTALL = "packagegroup-meraki-compliance ${CORE_IMAGE_EXTRA_INSTALL}"
inherit core-image
inherit kernel-arch
LICENSE = "GPLv2"
IMAGE_FSTYPES = "${INITRAMFS_FSTYPES}"
IMAGE_ROOTFS_SIZE ?= "8192"
IMAGE_ROOTFS_EXTRA_SPACE_append = "${@bb.utils.contains("DISTRO_FEATURES", "systemd", " + 4096", "", d)}"
INCOMPATIBLE_LICENSE = "CLOSED"
