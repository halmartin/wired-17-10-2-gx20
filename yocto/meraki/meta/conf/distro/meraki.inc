# Distribution definition for: meraki
#
# Copyright (c) 2011, Intel Corporation.
# All rights reserved.
#
# This file is released under the MIT license as described in
# poky/meta/COPYING.MIT.
#

# Fetch all files from dl.meraki.net if possible
SOURCE_MIRROR_URL = "https://dl.meraki.net/"
INHERIT += "own-mirrors"

UNINATIVE_URL = "${SOURCE_MIRROR_URL}uninative/1.9/"

PREFERRED_VERSION_util-linux = "2.34"
PNBLACKLIST[util-linux] ?= "${@'Only version ' + d.getVar('PREFERRED_VERSION_${BPN}') + ' (not ${PV}) is supported in this configuration. ' if d.getVar('PREFERRED_VERSION_${BPN}') and not d.getVar('PV').startswith(d.getVar('PREFERRED_VERSION_${BPN}').replace('%', '')) else ''}"

BINUVERSION = "2.35%"

PREFERRED_PROVIDER_libdevmapper = "lvm2"

RDEPENDS_kernel-base = ""

# Avoid installing "recommended" packages
NO_RECOMMENDATIONS = "1"

# Where we're going... we don't need no dev_manager!
VIRTUAL-RUNTIME_dev_manager = ""

# Where we're going... we don't need no init_manager!
VIRTUAL-RUNTIME_init_manager = ""

# Disable graphics
XSERVER = ""

# Don't create symlinks for externalsrc. They pollute the source directories.
EXTERNALSRC_SYMLINKS = ""

# remove the ROOTFS_RO_UNNEEDED packages like base-passwd and update-alternatives
FORCE_RO_REMOVE = "1"

# NOTE We do not add "pam" here as we don't want to enable libpam support in
# recipes; we do however build libpam independently.
DISTRO_FEATURES="pci ipv4 ipv6 usbhost largefile gobject-introspection-data"

include build-configuration.inc
