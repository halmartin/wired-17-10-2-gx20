# remove _all_ the runtime dependencies in the base package
RDEPENDS_${PN} = ""
RDEPENDS_${PN} += " uidmapshift"

# Don't install templates
PACKAGECONFIG_remove = "templates"
