package com.infinities.neutron.subnets.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * Please use {@link Subnet} directly.
 */
@SuppressWarnings("serial")
@JsonRootName("subnet")
public class SubnetForCreate extends Subnet {

    /**
     * @return the ipVersion
     */
    @JsonIgnore
    public int getIpVersion() {
        return getIpversion().code();
    }

    /**
     * @param ipVersion
     *            the ipVersion to set
     */
    @JsonIgnore
    public void setIpVersion(int ipVersion) {
        setIpversion(IpVersion.valueOf(ipVersion));
    }
}
