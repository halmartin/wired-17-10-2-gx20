FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://0002-Enable-optional-fwd_interface-for-fwd-policies.patch \
            file://0003-Enable-line-buffering-for-stdout.patch \
            file://CVE-2021-45079.patch \
           "

EXTRA_OECONF += " \
        --with-gnu-ld \
        --with-ipsecdir=/usr/sbin \
        --disable-defaults \
        --enable-libtool-lock \
"

DEFAULT_CONFIG = "charon ikev1 ikev2 scripts swanctl monolithic"
STRONGSWAN_CONFIG = "aes des ctr hmac md5 openssl nonce random sha1 sha2"
CHARON_CONFIG = "attr coupling netlink socketdefault systimefix vici"
PACKAGECONFIG = "${DEFAULT_CONFIG} \
                 ${STRONGSWAN_CONFIG} \
                 ${CHARON_CONFIG} \
                "

PACKAGECONFIG[aes] = "--enable-aes,--disable-aes,,${PN}-plugin-aes"
PACKAGECONFIG[attr] = "--enable-attr,--disable-attr,,${PN}-plugin-attr"
PACKAGECONFIG[coupling] = "--enable-coupling,--disable-coupling,,${PN}-plugin-coupling"
PACKAGECONFIG[ctr] = "--enable-ctr,--disable-ctr,,${PN}-plugin-ctr"
PACKAGECONFIG[des] = "--enable-des,--disable-des,,${PN}-plugin-des"
PACKAGECONFIG[hmac] = "--enable-hmac,--disable-hmac,,${PN}-plugin-hmac"
PACKAGECONFIG[ikev1] = "--enable-ikev1,--disable-ikev1,"
PACKAGECONFIG[ikev2] = "--enable-ikev2,--disable-ikev2,"
PACKAGECONFIG[netlink] = "--enable-kernel-netlink,--disable-kernel-netlink,,${PN}-plugin-kernel-netlink"
PACKAGECONFIG[md5] = "--enable-md5,--disable-md5,,${PN}-plugin-md5"
PACKAGECONFIG[monolithic] = "--enable-monolithic,--disable-monolithic,"
PACKAGECONFIG[nonce] = "--enable-nonce,--disable-nonce,,${PN}-plugin-nonce"
PACKAGECONFIG[random] = "--enable-random,--disable-random,,${PN}-plugin-random"
PACKAGECONFIG[scripts] = "--enable-scripts,--disable-scripts,"
PACKAGECONFIG[sha1] = "--enable-sha1,--disable-sha1,,${PN}-plugin-sha1"
PACKAGECONFIG[sha2] = "--enable-sha2,--disable-sha2,,${PN}-plugin-sha2"
PACKAGECONFIG[socketdefault] = "--enable-socket-default,--disable-socket-default,,${PN}-plugin-socket-default"
PACKAGECONFIG[systimefix] = "--enable-systime-fix,--disable-systime-fix,,${PN}-plugin-systime-fix"
PACKAGECONFIG[vici] = "--enable-vici,--disable-vici,,${PN}-plugin-vici"

FILES_${PN} += "${sbindir}/swanctl"
FILES_${PN}-dbg += "${datadir}"

# The list below is the difference of the RDEPENDS list from
# meta-backports-hardknott/recipes-support/strongswan/strongswan_5.9.2.bb and
# the PACKAGECONFIGs above.
# We use --disable-defaults above, so the base recipe's assumption that these
# plugins are installed by default is wrong. This list should be updated as
# required in future strongswan upgrades.
RDEPENDS_${PN}_remove = "\
    ${PN}-plugin-cmac \
    ${PN}-plugin-constraints \
    ${PN}-plugin-dnskey \
    ${PN}-plugin-pem \
    ${PN}-plugin-pgp \
    ${PN}-plugin-pkcs1 \
    ${PN}-plugin-pkcs7 \
    ${PN}-plugin-pkcs8 \
    ${PN}-plugin-pkcs12 \
    ${PN}-plugin-pubkey \
    ${PN}-plugin-rc2 \
    ${PN}-plugin-resolve \
    ${PN}-plugin-revocation \
    ${PN}-plugin-sshkey \
    ${PN}-plugin-updown \
    ${PN}-plugin-x509 \
    ${PN}-plugin-xauth-generic \
    ${PN}-plugin-xcbc \
    ${PN}-plugin-curve25519 \
    "
