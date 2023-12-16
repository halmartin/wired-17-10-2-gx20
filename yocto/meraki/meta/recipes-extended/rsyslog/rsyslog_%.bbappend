
SRC_URI_append := "file://000-imchardevice.patch \
                   file://contrib/imchardevice/ \
"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"

inherit autotools

PACKAGECONFIG_append = "imchardevice"
PACKAGECONFIG_remove = "imfile"

PACKAGECONFIG[imchardevice] = "--enable-imchardevice,--disable-imchardevice,,"

do_configure_prepend() {
    cp -r ${WORKDIR}/contrib/imchardevice ${WORKDIR}/${P}/contrib/
}

do_install_append() {
    install -d "${D}${sysconfdir}/rsyslog.d"
}
