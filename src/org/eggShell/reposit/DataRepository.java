package org.eggShell.reposit;


public interface DataRepository {

    Long incrAndGetCount(String key, Long windowStartTime, Long currentRequestTime);

}
