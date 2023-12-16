SRC_URI_prepend = "https://dl.meraki.net/busybox-${PV}.tar.bz2 "
SRC_URI[md5sum] = "9812d59bd5ff22bd424dabded393b52d"
SRC_URI[sha256sum] = "ec8d1615edb045b83b81966604759c4d4ac921434ab4011da604f629c06074ce"

# busybox has different config depending on platform
PACKAGE_ARCH = "${MACHINE_ARCH}"

# Split our source files for clarity
FILESEXTRAPATHS_prepend := "${THISDIR}/openwrt:${THISDIR}/meraki:${THISDIR}/configs/${MACHINE}:"

# Busybox can be configured to use its own crypto functions or those from the
# system. As of Thud, by default yocto configures it to use the built-in
# functions so there's no need to depend on any other crypto. If
# CONFIG_USE_BB_CRYPT is ever disabled, this dependency should be added back.
DEPENDS_remove += "virtual/crypt"

# Avoid some defaults set by Yocto
SRC_URI_remove := " \
    file://login-utilities.cfg \
    file://mount-via-label.cfg \
    "

# Add a patch to busybox that can be removed when yocto bumps busybox to 1.34.1
FILESEXTRAPATHS_prepend := "${THISDIR}/busybox:"
SRC_URI += " \
    file://0001-udhcp-fix-build-breakage-on-MIPS.patch \
    "

# Apply our OpenWRT patches
SRC_URI += " \
    file://0175-udhcp-request-mtu.patch \
    file://0910-grep-unbuffered.patch \
    file://0921-reboot_log_reason.patch \
    file://0923-arping_rate.patch \
    file://0993-ping-flush-stdout.patch \
    file://0994-ping-print-timeouts.patch \
    file://0995-syslogd.patch \
    file://0996-dmalloc.patch \
    file://1000-add-busybox_suffix.patch \
    file://1100-add-tftp-B-option.patch \
    file://1200-add-patch-outfile-option.patch \
    file://200-udhcpc_reduce_msgs.patch \
    file://201-udhcpc_changed_ifindex.patch \
    file://210-add_netmsg_util.patch \
    file://220-add_lock_util.patch \
    file://270-libbb_make_unicode_printable.patch \
    file://301-ip-link-fix-netlink-msg-size.patch \
    file://400-compress-rotated-logfile.patch \
    "

python() {
    machine = d.getVar("MACHINE")
    paths = d.getVar("FILESEXTRAPATHS")

    import os
    for path in paths.split(":"):
        # Make sure there's a MACHINE-specific defconfig present.
        if os.path.exists(os.path.join(path, "defconfig")) and machine in path:
            return

    raise Exception("Missing defconfig for " + machine)
}

# Since some of our platforms use rsyslog, the `busybox-syslog` package cannot
# be installed. As syslogd and klogd are orthogonal, this makes klogd installed
# as part of the `busybox` package instead of `busybox-syslog`.
ALTERNATIVE_${PN}_append = " klogd"
ALTERNATIVE_${PN}-syslog_remove = "klogd"

# Something about the way busybox and shadow each installs nologin breaks the
# ability of alternatives to resolve it properly. It showed up in busybox in
# 1.32; prior to that it only existed in shadow. So we'll pretend that's still
# the case and let the shadow version prevail.
#
# It's possible that this is related to the empty pkg_postinst in shadow that
# we have to add for manufacturing.
ALTERNATIVE_${PN}_remove = "nologin"
