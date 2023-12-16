SUMMARY = "An L2TP client/server, designed for VPN use."
DESCRIPTION = "OpenL2TP is an open source L2TP client / server, written \
specifically for Linux. It has been designed for use as an enterprise \
L2TP VPN server or in commercial, Linux-based, embedded networking \
products and is able to support hundreds of sessions, each with \
different configuration. It is used by several ISPs to provide \
L2TP services and by corporations to implement L2TP VPNs."
HOMEPAGE = "http://www.openl2tp.org/"
SECTION = "net"

# cli and usl use license LGPL-2.1
LICENSE = "GPL-2.0 & LGPL-2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=e9d9259cbbf00945adc25a470c1d3585 \
                    file://LICENSE;md5=f8970abd5ea9be701a0deedf5afd77a5 \
                    file://cli/LICENSE;md5=9c1387a3c5213aa40671438af3e00793 \
                    file://usl/LICENSE;md5=9c1387a3c5213aa40671438af3e00793 \
                    "

DEPENDS = "popt flex-native bison-native readline"
RDEPENDS_${PN} = "ppp ppp-l2tp"

SRC_URI = "ftp://ftp.openl2tp.org/releases/${BP}/${BP}.tar.gz \
           file://001-decline-to-add-fd-over-FD_SETSIZE.patch \
           file://001-makefile.patch \
           file://002-no_libfl.patch \
           file://010-ppp_unix.patch \
           file://020-no_client_and_add_stats.patch \
           file://021-ppp_optionsfile.patch \
           file://022-load_openl2tp_plugin_without_rpc.patch \
           file://023-fix_plugin_unload.patch \
           file://024-detached_session_clean_up_fix.patch \
           file://025-change_setkey_path.patch \
           file://026-stdout_line_buffering.patch \
           file://027-pass_remote_ip_to_ppp.patch \
           file://100-fix-unused-result.patch \
           file://110-musl.patch \
           file://120-close-fd-on-failure-to-add.patch \
           file://0001-l2tp_api-Included-needed-headers.patch \
           file://0004-Adjust-for-linux-kernel-headers-assumptions-on-glibc.patch \
           file://ppp-unix-musl.patch \
           "
SRC_URI[md5sum] = "e3d08dedfb9e6a9a1e24f6766f6dadd0"
SRC_URI[sha256sum] = "1c97704d4b963a87fbc0e741668d4530933991515ae9ab0dffd11b5444f4860f"

inherit autotools-brokensep pkgconfig

DEPENDS_append_libc-musl = " libtirpc"
CPPFLAGS_append_libc-musl = " -I${STAGING_INCDIR}/tirpc"
CFLAGS_append_libc-musl = " -I${STAGING_INCDIR}/tirpc"
LDFLAGS_append_libc-musl = " -ltirpc"

python() {
    # Newer gcc complains about format-overflow in l2tp_statusfile.c openl2tp is
    # really old source code that I am not inclined to fix right now.
    if int(d.getVar("GCCVERSION")[0]) >= 7:
        d.appendVar("CPPFLAGS",  " -Wno-error=format-overflow ")
}

do_compile_prepend() {
    sed -i -e "s:SYS_LIBDIR=.*:SYS_LIBDIR=${libdir}:g" \
        -e 's:$(CROSS_COMPILE)as:${AS}:g' \
        -e 's:$(CROSS_COMPILE)ld:${LD}:g' \
        -e 's:$(CROSS_COMPILE)gcc:${CC}:g' \
        -e 's:$(CROSS_COMPILE)ar:${AR}:g' \
        -e 's:$(CROSS_COMPILE)nm:${NM}:g' \
        -e 's:$(CROSS_COMPILE)strip:${STRIP}:g' \
        -e 's:$(CROSS_COMPILE)install:install:g' \
        ${S}/Makefile
}

do_install_append() {
    # This directories is empty so just delete it.
    rm -rf ${D}/usr/bin
}

PARALLEL_MAKE = ""
EXTRA_OEMAKE = 'CPPFLAGS="${CPPFLAGS} ${LDFLAGS}"'

INSANE_SKIP_${PN} += "ldflags"
