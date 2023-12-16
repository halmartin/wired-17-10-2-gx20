inherit kernel externalsrc
require recipes-kernel/linux/linux-yocto.inc

# kernel.bbclass requests externalsrc.bbclass to remove this task if the source
# is fetched through externalsrc. However, linux-libc-headers:do_install
# depends on this task. So make sure it's not removed and just mark it as
# noexec instead.
#
# NOTE: do_patch depends on do_unpack, and do_unpack makes sure
#       STAGING_KERNEL_DIR exists and points to the correct sources for
#       externalsrc case. The only reason do_fetch and do_unpack aren't deleted
#       by externalsrc.bbclass is because we have a local uri to fetch (namely
#       defconfig). If that's removed, this will no longer work.
KERNEL_CONFIG_TASKS = "${@oe.utils.ifelse('${KERNEL_FEATURES}'.strip(), 'do_kernel_configme do_kernel_configcheck', '')}"
SRCTREECOVEREDTASKS_remove = "do_patch ${KERNEL_CONFIG_TASKS}"
do_patch() {
}

do_configure_append() {
    if [ -z "${KERNEL_FEATURES}" ]; then
        # A "feature" (a.k.a bug) in upstream yocto recipe will copy the defconfig
        # to $B/.config *only* if $B/.config doesn't already exist. This is clearly a
        # bug because if defconfig is updated, it should definitely be copied again.
        cp "${WORKDIR}/defconfig" "${B}/.config"
    fi
}

python() {
    # save version from filename
    d.setVar('MAJOR_VERSION', d.getVar('PV', True))

    # get actual kernel version
    import re
    var_eq_int_re = re.compile(r'^(\w+) = (\d+)$')
    makefile_path = os.path.join(d.getVar('EXTERNALSRC', True), 'Makefile')
    store = {}
    with open(makefile_path) as fd:
        for line in fd:
            match = var_eq_int_re.match(line)
            if not match:
                continue
            name, value = match.groups()
            store[name] = int(value)
    d.setVar('PV', '{VERSION}.{PATCHLEVEL}.{SUBLEVEL}'.format(**store))
}

EXTERNALSRC = "${ROUTER_ROOT}/linux-${MAJOR_VERSION}"

LINUX_VERSION = "${PV}"
LINUX_VERSION_EXTENSION = "-meraki"

# Set this variable so that the do_install_append in linux-yocto.inc will
# remove this directory after the build is done.
KMETA = ".kernel-meta"

# Remove artifacts from the kernel source directory.
do_install_append() {
    rm -rf ${STAGING_KERNEL_DIR}/.scmversion
    rm -rf ${STAGING_KERNEL_DIR}/.metadir
}

require platform.inc
require module-signing.inc
