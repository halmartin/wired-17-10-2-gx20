# libelf is LGPLv3, so no
DEPENDS_remove += "elfutils"

# remove the default tipc PACKAGECONFIG only required by polaris switch
PACKAGECONFIG = ""
PACKAGECONFIG_meraki-ms-polaris = "tipc"

do_install_append() {
    # remove-if-empty /usr/lib/tc
    rmdir -p ${D}${libdir}/tc || true
}