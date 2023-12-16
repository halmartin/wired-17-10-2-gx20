FILESEXTRAPATHS_prepend := "${THISDIR}/openwrt:${THISDIR}/${BPN}:${THISDIR}/dictionaries:"

# All non-dictionaries are patches from OpenWRT
SRC_URI += " \
    file://dictionary \
    file://dictionary.asnet \
    file://dictionary.microsoft \
    \
    file://010-use_target_for_configure.patch \
    file://100-debian_ip-ip_option.patch \
    file://101-debian_close_dev_ppp.patch \
    file://103-debian_fix_link_pidfile.patch \
    file://105-debian_demand.patch \
    file://106-debian_stripMSdomain.patch \
    file://107-debian_pppoatm_wildcard.patch \
    file://110-debian_defaultroute.patch \
    file://120-debian_ipv6_updown_option.patch \
    file://121-debian_adaptive_lcp_echo.patch \
    file://133-fix_sha1_include.patch \
    file://140-pppoe_compile_fix.patch \
    file://200-makefile.patch \
    file://201-mppe_mppc_1.1.patch \
    file://202-no_strip.patch \
    file://203-opt_flags.patch \
    file://204-radius_config.patch \
    file://205-no_exponential_timeout.patch \
    file://206-compensate_time_change.patch \
    file://207-lcp_mtu_max.patch \
    file://208-fix_status_code.patch \
    file://300-filter-pcap-includes-lib.patch \
    file://310-precompile_filter.patch \
    file://321-multilink_support_custom_iface_names.patch \
    file://330-retain_foreign_default_routes.patch \
    file://340-populate_default_gateway.patch \
    file://400-simplify_kernel_checks.patch \
    file://401-no_record_file.patch \
    file://403-no_wtmp.patch \
    file://404-remove_obsolete_protocol_names.patch \
    file://405-no_multilink_option.patch \
    file://410-save-pppoe-connection-status.patch \
    file://411-add-remoteip-plugin.patch \
    file://413-add-ipfromclick-plugin.patch \
    file://431-increase-max-password-length.patch \
    file://500-add-pptp-plugin.patch \
    file://510-pptp_compile_fix.patch \
    file://511-pptp_cflags.patch \
    file://531-pppoe_no_disconnect_warning.patch \
    file://540-save-pppol2tp_fd_str.patch \
    file://600-more_wtmp_removal.patch \
    file://600-Revert-pppd-Use-openssl-for-the-DES-instead-of-the-l.patch \
"

SRC_URI_append += " \
    file://pptp-Fix-musl-compilation.patch \
"

# The following patches are removed. This unnecessary addition/removal is for
# documentation purposes.
#
# 202-no_strip.patch - duplicated by Yocto patch makefile.patch
# 203-opt_flags.patch - partially duplicated by Yocto patch copts.patch; other changes are unnecessary
# 300-filter-pcap-includes-lib.patch - unnecessary in Yocto
# 140-pppoe_compile_fix.patch - fixes for glibc that break other things
SRC_URI_remove += " \
    file://202-no_strip.patch \
    file://203-opt_flags.patch \
    file://300-filter-pcap-includes-lib.patch \
    file://140-pppoe_compile_fix.patch \
"

# Clientvpn requires libpam integration
DEPENDS += "${@bb.utils.contains('MERAKI_FEATURES', 'clientvpn', 'libpam', '', d)}"
EXTRA_OEMAKE += " \
    STAGING_DIR=${STAGING_DIR_TARGET} \
    ${@bb.utils.contains('MERAKI_FEATURES', 'clientvpn', 'USE_PAM=y', '', d)} \
"

FILES_${PN} += "${libdir}/pppd/${PV}/pptp.so"
FILES_${PN} += "${libdir}/pppd/${PV}/ipfromclick.so"
FILES_${PN} += "${libdir}/pppd/${PV}/remoteip.so"

FILES_${PN}-radius += "${sysconfdir}/ppp/radius/dictionary*"

do_install() {
    autotools_do_install
    mkdir -p ${D}${sysconfdir}/ppp/peers
    mkdir -p ${D}${sysconfdir}/ppp/radius
    install -m644 ${WORKDIR}/dictionary* ${D}${sysconfdir}/ppp/radius
    # main recipe appends to do_install
    # we don't want that to happen, hence:
    return
}

# Added for python backported from Yocto Zeus (3.0) while we're still on Rocko
# (2.4). Virtual/crypt is added in Thud (2.6), so once we upgrade that far this
# line should be removed.
DEPENDS_remove += "virtual/crypt"
