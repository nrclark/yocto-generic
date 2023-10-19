
IMAGE_FSTYPES = "container oci"
PREFERRED_PROVIDER_virtual/kernel = "linux-dummy"

inherit image
inherit image-oci


IMAGE_LINGUAS = ""
#NO_RECOMMENDTIONS = "1"
IMAGE_FEATURES = ""
#ROOTFS_BOOTSTRAP_INSTALL = ""
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

#IMAGE_INSTALL += "nginx bash coreutils util-linux grep findutils which openssh rsync vim hashserv procps"
IMAGE_INSTALL += "nginx openssh hashserv bash rsync vim busybox"
OCI_IMAGE_TAG = "cache-server"
OCI_IMAGE_ENTRYPOINT = "/bin/bash"
#OCI_IMAGE_ENTRYPOINT ?= "sh"
#OCI_IMAGE_ENTRYPOINT_ARGS ?= ""
#OCI_IMAGE_WORKINGDIR ?= ""

# List of ports to expose from a container running this image:
#  PORT[/PROT]
#     format: <port>/tcp, <port>/udp, or <port> (same as <port>/tcp).
OCI_IMAGE_PORTS ?= ""

# key=value list of labels
#OCI_IMAGE_LABELS ?= ""
# key=value list of environment variables
#OCI_IMAGE_ENV_VARS ?= ""
