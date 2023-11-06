PREFERRED_PROVIDER_virtual/kernel = "linux-yocto"
require recipes-core/images/core-image-minimal.bb

IMAGE_FEATURES += "\
    ssh-server-openssh \
    debug-tweaks \
"

IMAGE_INSTALL += "\
    curl \
    bash \
    nginx \
    bitbake-hashserv \
    rsync \
    vim \
    python3-ipython \
    python3-modules \
    sstate-mgr \
"

add_proxy_config () {
    install -d "${IMAGE_ROOTFS}/etc/profile.d"
    install -m 0644 "${THISDIR}/files/proxy.sh" "${IMAGE_ROOTFS}/etc/profile.d"
}

ROOTFS_POSTPROCESS_COMMAND += "add_proxy_config; "
IMAGE_ROOTFS_EXTRA_SPACE = "${@ 8*2**20}"
