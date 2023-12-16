# Distro config is evaluated after the machine config, so we have to explicitly
# set the kernel provider to override a machine config.
PREFERRED_PROVIDER_virtual/kernel = "linux-meraki"

# Distro config is evaluated after the machine config, so we have to explicitly
# set the kernel provider to override a machine config.
PREFERRED_PROVIDER_linux-libc-headers = "meraki-linux-libc-headers"

# We always want to use the same version for linux-libc-headers as the kernel.
# Poky takes a different approach always uses a "standard" set of headers from
# an upstream kernel. That doesn't make any sense and so we have our own recipe
# for linux-libc-headers that uses the main kernel's sources and therefore
# should match the version of the main kernel.
LINUXLIBCVERSION ?= "${PREFERRED_VERSION_linux-meraki}"
LINUX_VERSION = "${PREFERRED_VERSION_linux-meraki}"
