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
	@$(eval NEW_PS1 := \[\e[31m\][bitbake]\[\e[m\] \[\033[01;32m\]\u@\h\[\033[00m\]:\[\033[01;34m\]\w\[\033[00m\]$$)
	@source submodules/poky/oe-init-build-env $(abspath .) && \
	unset PROMPT_COMMAND && \
	unset MAKE_TERMOUT && \
	unset MAKE_TERMERR && \
	unset MAKEFLAGS && \
	unset MAKELEVEL && \
	$(SHELL) --rcfile <(cat ~/.bashrc; echo "PS1=\"$(NEW_PS1) \"") || exit 0

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

#------------------------------------------------------------------------------#
# This section creates a custom BOOT_PARAMS variable that is passed
# to runqemu. The contents eventually makes its way into /proc/cmdline
# inside of the VM, where it is interpreted by /etc/profile.d/proxy.sh to
# configure the proxy settings.

ifneq ($(http_proxy),)
COMMA := ,
EMPTY :=
SPACE := $(EMPTY) $(EMPTY)
PROXY := $(patsubst %/,%,$(subst 127.0.0.1,10.0.2.2,$(http_proxy)))

BOOT_PARAMS := proxy=$(PROXY)
NOPROXY_FILTERS := $(strip \
	$(foreach x,$(shell seq 16 31),172.$x.%) \
	127.% \
	::1 \
	localhost \
	192.168.% \
	10.% \
)

NO_PROXY := $(strip $(subst $(SPACE),$(COMMA),$(strip \
	$(filter-out $(NOPROXY_FILTERS),$(subst $(COMMA), ,$(no_proxy))) \
)))

ifneq ($(NO_PROXY),)
BOOT_PARAMS += no_proxy=$(NO_PROXY)
endif
endif

#------------------------------------------------------------------------------#
fuck:
	@echo $(BOOT_PARAMS)

#echo "$(foreach x,$(shell seq 16 31),172.$x.%)"

launch:
	source submodules/poky/oe-init-build-env $(abspath .) && \
	export IMAGE_LINK_NAME=core-image-custom-qemux86-64 && \
	runqemu qemux86-64 $(IMAGE) nographic serial kvm slirp $(if $(BOOT_PARAMS),bootparams="$(BOOT_PARAMS)",)

launch2:
	source submodules/poky/oe-init-build-env $(abspath .) && \
	export IMAGE_LINK_NAME=core-image-custom-qemux86-64 && \
	runqemu qemux86-64 $(IMAGE) nographic kvm

showdeps:
	@cat submodules/poky/documentation/poky.ent | tr -d '\n' | \
		grep -Po '(?<=UBUNTU_HOST_PACKAGES_ESSENTIAL)[^>]+' | tr -d '["\\]' | \
		tr ' ' '\n' | sort | uniq | grep .
