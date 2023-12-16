# Meraki broadcom PoE uses specific static inline functions defined in i2c-dev.h from i2c-tools
# that is not available in standard linux i2c-dev.h header.
do_install_append() {
    install -d ${D}/${includedir}/i2c-tools/
    install -m 0644 ${D}/${includedir}/linux/i2c-dev-user.h ${D}/${includedir}/i2c-tools/i2c-dev.h
}
