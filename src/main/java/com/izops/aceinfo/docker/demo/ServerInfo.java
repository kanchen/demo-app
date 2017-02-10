package com.izops.jersey.docker.demo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
// JAX-RS supports an automatic mapping from JAXB annotated class to XML and JSON
// Isn't that cool?
public class ServerInfo {
        private String version;
        private String ip;
        public String getVersion() {
                return version;
        }
        public void setVersion(String version) {
                this.version = version;
        }
        public String getIp() {
                return ip;
        }
        public void setIp(String ip) {
                this.ip = ip;
        }
}
