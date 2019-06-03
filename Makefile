SHELL := /bin/bash
IMAGE := core-image-custom

all default: build

distclean: clean
	rm -rf downloads

clean:
	rm -rf cache/ bitbake-cookerdaemon.log sstate-cache/ tmp

build:
	source submodules/poky/oe-init-build-env $(abspath .) && \
	bitbake $(IMAGE)

shell:
	source submodules/poky/oe-init-build-env $(abspath .) && \
	$(SHELL)

# Developer's note: SLIRP mode communication seems to work pretty
# well. It doesn't handle outgoing pings correctly though, which
# can make it easy to expect that something isn't working.
#
# To get around this, a sysctl value can be used to allow an
# unprivileged to ping out. The magic command is:
#
#     sudo sysctl net.ipv4.ping_group_range "$GID $GID"
#
# where $GID is the numerical value of some group that the target
# user is in. This can also be done permanently be editing your
# # system's sysctl.conf (commonly /etc/sysctl.conf).

launch:
	source submodules/poky/oe-init-build-env $(abspath .) && \
	runqemu qemux86-64 $(IMAGE) nographic serial kvm slirp

launch_full:
	source submodules/poky/oe-init-build-env $(abspath .) && \
	runqemu qemux86-64 $(IMAGE) slirp

