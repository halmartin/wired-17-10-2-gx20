DESCRIPTION = "Dynamic routing daeomon"
HOMEPAGE = "http://bird.network.cz/"
LICENSE = "GPLv2"
SECTION = "connectivity"
LIC_FILES_CHKSUM = "file://LICENSE-meraki;md5=1f0a42ec48e7a3fdf869e49b1e939adc"

SRC_URI = "http://dl.meraki.net/bird-${PV}.tar.gz"
SRC_URI[md5sum] = "abe29a927e21a3d7f8092641f06093c2"
SRC_URI[sha256sum] = "6c61ab5d2ef59d2559a8735b8252b5a0238013b43e5fb8a96c5d9d06e7bc00b2"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI += "file://0001-add-meraki-license.patch \
            file://0002-sysdep-unix-transform-unix-into-click-sysdep.patch \
            file://0003-sysdep-click-Copy-unix-to-click.patch \
            file://0004-sysdep-click-add-click.patch \
            file://0005-sysdep-unix-Revert-unix-sysdep-layer.patch \
            file://0006-bird-Add-generate-never-config-option-for-passwords.patch \
            file://0007-bird-Add-CLI-command-to-export-OSPF-neighbors-in-JSO.patch \
            file://0008-bird-Add-event-logging-mechanism.patch \
            file://0009-bird-Event-logging-for-OSPF-neighbor-state-changes.patch \
            file://0010-bird-Filter-out-OSPF-routes-that-overlap-interfaces.patch \
            file://0011-bird-add-meraki-protocol-to-bird-and-enable-it-and-B.patch \
            file://0012-bird-meraki-proto-only-emit-primary-routes-into-conf.patch \
            file://0013-bird-wired_brain-bird.conf-use-graceful-failover-in-.patch \
            file://0014-bird-Changed-ip_ntop-calls-to-ip4_ntop.patch \
            file://0015-bird-Added-support-for-multipath-route-notifications.patch \
            file://0016-bird-Removed-static-declarations-from-header-file.patch \
            file://0017-bird-Fixed-compiler-warnings-in-Click-sysdep.patch \
            file://0018-bgp-Report-BGP-route-info-to-Dashboard.patch \
            file://0019-route_attr-Add-extra-source-related-attributes.patch \
            file://0020-Bird-add-header-file-with-macros-to-be-used-in-merak.patch \
            file://0021-Bird-Give-higher-precedence-to-dashboard-routes-over.patch \
            file://0022-bird-Fixed-call-to-ip4_ntop-that-should-have-been-ip.patch \
            file://0023-BGP-Write-prefixes-with-short-prefix-lengths-correct.patch \
            file://0024-BIRD-Removed-uninformative-log-line.patch \
            file://0025-BGP-Add-event-logs-for-BGP.patch \
            file://0026-BIRD-Fix-local-compilation-warnings-in-proto-meraki-.patch \
            file://0027-BGP-Support-forwarding-to-third-party-next-hops.patch \
            file://0028-BIRD-Teach-BIRD-s-meraki_config-about-EBGP-peer-addr.patch \
            file://0029-BGP-Firmware-support-for-BGP-peer-information-grabbe.patch \
            file://0030-bird-Teach-bird-about-gateways-known-through-config.patch \
            file://0031-BGP-Move-export-bgp-peers-implementation-to-meraki-d.patch \
            file://0032-MX-BIRD-manages-routes-MX-click-installer-installs-t.patch \
            file://0033-bird-Free-unused-memory.patch \
            file://0034-bird-fix-out-of-bounds-write-in-meraki_notify.patch \
            file://0035-bird-Optimize-creation-of-bird-generated-Meraki-conf.patch \
            file://0036-libmeraki-remove-global-include_directories-calls-pa.patch \
            file://0037-meraki_proto-Add-new-meraki_peer_offline-route-attri.patch \
            file://0038-add-flock-when-reading-config-file.patch \
            "

S = "${WORKDIR}/bird-${PV}"

inherit autotools externalsrc pkgconfig

DEPENDS += "bison-native flex-native libsockproxy"

# Excluding aclocal prevents removing aclocal.m4 (which is custom for bird and
# can't be removed.  Excluding autoheader prevents the "autoheader failed with
# exit code 1" error.
EXTRA_AUTORECONF += "--exclude=aclocal --exclude=autoheader"
EXTRA_OECONF += "--with-protocols=bgp,ospf,static,pipe,meraki \
                 --with-libsockproxydir=${STAGING_DIR_TARGET}/usr \
                 --disable-client \
                 "

#do_configure() {
#    mkdir -p ${STAGING_DIR_TARGET}/usr/include/libmeraki/misc/libmeraki/  
#    install -m0755 -D ${ROUTER_ROOT}/meraki/libmeraki/ ${STAGING_DIR_TARGET}/usr/include/libmeraki/misc/libmeraki/
#}

EXTRA_OECONF_append_meraki-ms = "--enable-click"

# For meraki_bird_defines.hh
#DEPENDS += "meraki-apps"
CPPFLAGS += "-I${ROUTER_ROOT}/meraki/"

do_install() {
  mkdir -p ${D}/usr/sbin/
  install -m0755 -D ${B}/bird ${D}/usr/sbin/bird
  install -m0755 -D ${B}/birdcl ${D}/usr/sbin/birdcl
  ln -s /usr/sbin/birdcl ${D}/usr/sbin/birdc
}

PACKAGES += " ${PN}-client "

FILES_${PN} = "/usr/sbin/bird"
FILES_${PN}-client = "/usr/sbin/birdcl /usr/sbin/birdc"
