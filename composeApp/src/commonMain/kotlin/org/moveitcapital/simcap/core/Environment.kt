package org.moveitcapital.simcap.core

private const val BASE_PATH = "/GeneralRS-0.0.1/api"
private const val IP_DEV = "http://192.168.110.21:1203"
private const val IP_QA = "http://192.168.110.21:1203"
private const val IP_PROD = "http://bksimcap.masivocapital.co:45201"
//Dev vpn pass: NVDesarrollo%24$*

enum class Environment(val baseUrl: String) {
    DEV(IP_DEV),
    QA(IP_QA),
    PROD(IP_PROD);
    
    fun general() = this.baseUrl + BASE_PATH
}

class EnvironmentProvider(val environment: Environment)