require recipes-core/images/core-image-minimal.bb

IMAGE_INSTALL += "\
    connman \
    connman-conf \
    connman-client \
"
