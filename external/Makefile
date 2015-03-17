# Twese two usually works
HOSTARCH := arm
HOSTPARM := --host=arm-linux
# HOSTPARM := --host=arm-linux-androideabi
#PREFIXT="mipsel-linux-android"
#PREFIXT="i686-linux-android"
PREFIXT?="arm-linux-androideabi"
# edit line below if you have NDK to reflect the path where NDK is installed
NDK := $(NDK_BASE)
NDKDEST=/tmp/ndk-$(HOSTARCH)
NDKPLATFORM?=android-9
ANDROIDDEST=/sdcard/opt/nmap-6.46

OPENSSLOPT=
# uncomment if you want openssl support and you have compiled OpenSSL already
# OPENSSLOPT="--with-openssl=/sdcard/opt/openssl"


all: | maketoolchain patch build export
	@echo Built Successfully 

maketoolchain:
	bash $(NDK)/build/tools/make-standalone-toolchain.sh --platform=$(NDKPLATFORM) --install-dir=$(NDKDEST) --arch=$(HOSTARCH)

patch:
	-cd nmap && patch -N -p1 < ../patches.diff

unpatch:
	-cd nmap && patch -R -p1 < ../patches.diff

build: | configure compile
	@echo "Type 'build' successfuly built"

configure:
	cd nmap && PATH=$(NDKDEST)/bin:$(PATH) LUA_CFLAGS="-DLUA_USE_POSIX" LDFLAGS="-static" \
	ac_cv_linux_vers=2 CC=$(PREFIXT)-gcc CXX=$(PREFIXT)-g++ LD=$(PREFIXT)-ld RANLIB=$(PREFIXT)-ranlib AR=$(PREFIXT)-ar STRIP=$(PREFIXT)-strip \
	./configure $(HOSTPARM) --without-zenmap --with-liblua=included --with-libpcap=internal --with-pcap=linux --enable-static --prefix=$(ANDROIDDEST) $(OPENSSLOPT)

compile:
	cd nmap && PATH=$(NDKDEST)/bin:$(PATH) make

clean: unpatch
	rm -rf $(NDKDEST)

export:
	mkdir -p includes
	cp nmap/nmap nmap/nmap-os-db nmap/nmap-payloads nmap/nmap-protocols nmap/nmap-rpc nmap/nmap-services nmap/nmap-service-probes ./includes/

showsetup:
	@echo "Current environment"
