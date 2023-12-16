BBPATH_prepend = "${THISDIR}/${PN}/${MACHINE}:"
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${MAJOR_VERSION}:"

inherit kernel
require recipes-kernel/linux/linux-yocto.inc

# openssl + perl required for kernel module signing
DEPENDS += "openssl-native perl-native"

SRC_URI = "git://${ROUTER_ROOT}/kernel-sources/;protocol=file;bareclone=1;branch=${KBRANCH}"

KBRANCH = "w17"
SRCREV = "${AUTOREV}"

KERNEL_VERSION_SANITY_SKIP="1"
LINUX_VERSION = "4.4.177"
LINUX_VERSION_EXTENSION = "-meraki"

require platform.inc
require module-signing.inc

