#ifndef _MERAKI_BIRD_DEFINES_H_
#define _MERAKI_BIRD_DEFINES_H_

#define BIRD_EXPORTED_ROUTE_BEGIN 100000
#define BIRD_EXPORTED_ROUTE_METRIC 100
#define BIRD_BGP_DEFAULT_HOLD_TIME 240

#define BIRD_EXPORTED_GATEWAY_BEGIN 100000

#define BIRD_ROUTES_EXPORT_FILE  "/tmp/bird_routes_export"
#define BIRD_ROUTES_JSON_FILE    "/tmp/bird_routes.json"

// Maximum preference value a protocol or route can have
#define MERAKI_BIRD_PREF_MAX             3000

// BIRD2 iBGP default 'connect retry time'
#define MERAKI_BIRD2_IBGP_PEER_DFLT_CONNECT_RETRY_TIME 10
// BIRD2 eBGP default 'connect retry time'
#define MERAKI_BIRD2_EBGP_PEER_DFLT_CONNECT_RETRY_TIME 30

// BIRD2 iBGP default 'connect delay time' after startup
#define MERAKI_BIRD2_IBGP_PEER_CONNECT_DELAY_TIME 1
// BIRD2 eBGP default 'connect delay time' after startup
#define MERAKI_BIRD2_EBGP_PEER_CONNECT_DELAY_TIME 5

// BIRD2 lower and upper bound for BGP wait time between protocol
// failure and automatic restart: 'error wait time <lower>, <upper>'
#define MERAKI_BIRD2_IBGP_PEER_ERROR_WAIT_TIME_FLOOR  1
#define MERAKI_BIRD2_IBGP_PEER_ERROR_WAIT_TIME_CELING 5
#define MERAKI_BIRD2_EBGP_PEER_ERROR_WAIT_TIME_FLOOR  20
#define MERAKI_BIRD2_EBGP_PEER_ERROR_WAIT_TIME_CELING 100

// BIRD2 maximum time in seconds between two protocol failures treat them
// as error sequence which makes 'error wait time' increase exponentially.
// This sets the value of the BIRD2 BGP peer parameter 'error forget time'
#define MERAKI_BIRD2_IBGP_PEER_ERROR_FORGET_TIME 10
#define MERAKI_BIRD2_EBGP_PEER_ERROR_FORGET_TIME 30

// Maximum priority value for a peer
#define MERAKI_PEER_PRIORITY_MAX 10

// Peer priority to use when priority is unspecified
#define MERAKI_PEER_PRIORITY_UNSPECIFIED 0

// A multiplier for ad used in preference calculation
#define MERAKI_BIRD_AD_TO_PREF_MULIPLIER 10

// Convert ad to preference
#define MERAKI_BIRD_AD_TO_PREF(ad)                     \
        (MERAKI_BIRD_PREF_MAX -                        \
        ((ad) * MERAKI_BIRD_AD_TO_PREF_MULIPLIER))

// Minimum configurable ad value
#define MERAKI_RTPROTO_AD_CFG_MIN        (MERAKI_RTPROTO_AD_DIRECT_LAN + 1)

// Maximum configurable ad value
#define MERAKI_RTPROTO_AD_CFG_MAX        254

// Check whether an ad value is in permissible range
#define MERAKI_RTPROTO_AD_CFG_VALID(ad)                \
   (((ad) >= MERAKI_RTPROTO_AD_CFG_MIN) && ((ad) <= MERAKI_RTPROTO_AD_CFG_MAX))

// Fixed and lowest AD for connected LAN routes so that they always
// have the highest preference
#define MERAKI_RTPROTO_AD_DIRECT_LAN            10

// Default AD values for routes and connected subnets -- keep these values in
// sync with the default values for administrative distance in
// wired_config_opts.hh
#define MERAKI_RTPROTO_DEFAULT_AD_DIRECT_WAN     15
#define MERAKI_RTPROTO_DEFAULT_AD_ANYCONNECTVPN  20
#define MERAKI_RTPROTO_DEFAULT_AD_CLIENTVPN      30
#define MERAKI_RTPROTO_DEFAULT_AD_STATIC         40
#define MERAKI_RTPROTO_DEFAULT_AD_AUTOVPN        50
#define MERAKI_RTPROTO_DEFAULT_AD_IPSECVPN       60
#define MERAKI_RTPROTO_DEFAULT_AD_OSPF           70
#define MERAKI_RTPROTO_DEFAULT_AD_BGP            80
#define MERAKI_RTPROTO_DEFAULT_AD_UL_DEFAULT     90

// Lower end value of reserved preference range
#define MERAKI_BIRD_RESV_PREF_MIN                                            \
        (MERAKI_BIRD_PREF_MAX -                                              \
        (MERAKI_RTPROTO_AD_DIRECT_LAN - 1) * MERAKI_BIRD_AD_TO_PREF_MULIPLIER)

// Upper end value of reserved preference range
#define MERAKI_BIRD_RESV_PREF_MAX        (MERAKI_BIRD_PREF_MAX - 1)

// Preference value associated with BIRD local1 protocol
#define MERAKI_BIRD_BOOSTER_PREF         (MERAKI_BIRD_RESV_PREF_MIN + 1)

// Preference value associated with Policy Based Route
#define MERAKI_BIRD_PBR_PREF             MERAKI_BIRD_RESV_PREF_MAX

// ad value has not been configured explicitly
#define MERAKI_RTPROTO_AD_UNSPECIFIED    -1

// This number signifies the interfaces created for AutoVPN
// in the bird namespace of the form bird_autovpn0, bird_autovpn1
#define NUM_BIRD_AUTOVPN_INTFS           2

// Maximum number of VLAN IDs that can be assigned on an interface
#define MAX_BIRD_VLAN_IDS                4094

// This defines the maximum number of AutoVPN peers we can support.
// Each of the above bird AutoVPN interfaces can support 4094 VLANs.
// Since each AutoVPN peer needs a VLAN for the remote routes to be
// taught to bird, the max AutoVPN peers are limited by maximum VLANs
// that can be configured
#define MAX_BIRD_AUTOVPN_PEERS           (NUM_BIRD_AUTOVPN_INTFS * MAX_BIRD_VLAN_IDS)

// Convert peer priority to route metric
#define MERAKI_PEER_PRIORITY_TO_ROUTE_METRIC(prio) (prio)

#endif /* _MERAKI_BIRD_DEFINES_H_ */
