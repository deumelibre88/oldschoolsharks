package pt.old.school.sharks.rocketleague.torneios;

import org.bson.types.ObjectId;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.NoOpDbRefResolver;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.MongoPersistentProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

/*
* Note that this example I've tried on spring-data-mongodb.3.0.4.RELEASE version 
* and it's not guaranteed that it will work with earlier versions 
* yet the approach should be similar
*/
@Component
public class CustomMappingMongoConverter extends MappingMongoConverter {

    public CustomMappingMongoConverter(MappingContext<? extends MongoPersistentEntity<?>, MongoPersistentProperty> mappingContext) {    
        //The constructor can differ based on the version and the dbRefResolver instance as well
        super(NoOpDbRefResolver.INSTANCE, mappingContext);
    }

    @Override
    public Object convertId(Object id, Class<?> targetType) {
        if (id == null) {
            return null;
        } else if (ClassUtils.isAssignable(ObjectId.class, targetType) && id instanceof String && ObjectId.isValid(id.toString())) {
            return id;
        }
        return super.convertId(id, targetType);
    }

}
