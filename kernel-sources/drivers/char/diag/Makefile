obj-$(CONFIG_DIAG_CHAR) := diagchar.o
obj-$(CONFIG_USB_QCOM_DIAG_BRIDGE) += diagfwd_hsic.o
obj-$(CONFIG_USB_QCOM_DIAG_BRIDGE) += diagfwd_smux.o
diagchar-objs := diagchar_core.o diagchar_hdlc.o diagfwd.o diagfwd_peripheral.o diag_mux.o diag_memorydevice.o diag_usb.o diagmem.o diagfwd_cntl.o diag_dci.o diag_masks.o diag_debugfs.o

ifdef CONFIG_QRTR
diagchar-objs += qcom_diagfwd_socket.o
else
diagchar-objs += diagfwd_socket.o
endif

ifdef CONFIG_DIAGFWD_BRIDGE_CODE
diagchar-objs += diagfwd_bridge.o

ifdef CONFIG_MSM_MHI
diagchar-objs += diagfwd_mhi.o
endif

endif
