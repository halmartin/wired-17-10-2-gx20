# Added for perl backported from Yocto Gatesgarth while we're still on Rocko.
# Virtual/crypt is added in Thud, so once we upgrade that far this
# line should be removed.
DEPENDS_remove += "virtual/crypt"

# The default packaging of perl-modules RRECOMMENDS scores of
# individual perl modules (eg., perl-module-encode-mime-name), but
# meraki images use NO_RECOMMENDATIONS to suppress extra unneeded
# packages in the image. Unfortunately, this breaks ptests which
# depend on perl-modules (such as openssl/ciscossl).
# Force the package list to be RDEPENDS instead so all the modules
# are installed
python split_perl_packages_append() {
    # remove module names in the remove_list from modules since
    # since they're not packages
    remove_list = { 'perl-module-config', 'perl-module-config-git',
                    'perl-module-strict', 'perl-module-vars',
                    'perl-module-warnings', 'perl-module-warnings-register'}
    modules = d.getVar(d.expand("RRECOMMENDS_${PN}-modules"))
    modules = ' '.join(filter(lambda x: x not in remove_list, modules.split()))
    d.setVar(d.expand("RDEPENDS_${PN}-modules"), modules)
    d.setVar(d.expand("RRECOMMENDS_${PN}-modules"), '')
}
