package com.jbb.mgt.core.domain.mapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jbb.mgt.core.domain.mq.RegisterEvent;
import com.jbb.server.common.concurrent.SimpleObjectPool;
import com.jbb.server.common.exception.ExecutionException;
import com.jbb.server.shared.map.KryoMapper;

/**
 * Strict object to byte[] and byte[] to object serialization mapper for internal objects
 */
public class Mapper {
    private static Logger logger = LoggerFactory.getLogger(Mapper.class);

    // The classes order must not be changed!!!
    // New classes must be added only to the end of this array!!!
    // To remove a class replace it to null.
    private static final Class[] registerClasses =
        {ArrayList.class, HashMap.class, Timestamp.class, RegisterEvent.class};

    private static final SimpleObjectPool<KryoMapper> kryoPool =
        new SimpleObjectPool<>(10, () -> new KryoMapper(registerClasses, 100));

    private static byte[] writeObject(Object object) {
        return kryoPool.call(k -> k.writeObject(object));
    }

    private static <T> T readObject(byte[] bytes, Class<T> clazz) {
        try {
            return kryoPool.call(k -> k.readObject(bytes, clazz));
        } catch (Exception e) {
            logger.error("Exception in reading " + clazz + "\nRegistered classes: " + Arrays.toString(registerClasses),
                e);
            throw new ExecutionException("Cannot read " + clazz, e);
        }
    }

    public static byte[] toBytes(RegisterEvent request) {
        return writeObject(request);
    }

    public static RegisterEvent readRegisterEvent(byte[] bytes) {
        return readObject(bytes, RegisterEvent.class);
    }

}
