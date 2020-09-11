package com.jfuerste.trackmydebtbackend.dto.mapper;

import com.jfuerste.trackmydebtbackend.domain.Transaction;
import com.jfuerste.trackmydebtbackend.domain.User;
import com.jfuerste.trackmydebtbackend.dto.TransactionDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserQualifier.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TransactionMapper {

    @Mapping(source = "sender", target = "sender")
    @Mapping(source = "sender", target = "senderId")
    @Mapping(source = "receiver", target = "receiver")
    @Mapping(source = "receiver", target = "receiverId")
    TransactionDTO transactionToTransactionDTO(Transaction transaction);

    @Mapping(source ="senderId", target = "sender", qualifiedByName = "userIdToUser")
    @Mapping(source ="receiverId", target = "receiver", qualifiedByName = "userIdToUser")
    Transaction transactionDtoToTransaction(TransactionDTO dto);


    default Long userToId(User user) {
        if (user == null){
            return null;
        }
        return user.getId();
    };
    default String userToString(User user) {
        if (user == null){
            return null;
        }
        return user.getEmail();
    };
}
