package org.revisao.services;

@ApplicationScoped
public class UserService {
    public List<UserEntity> findAll() {
        return UserEntity.listAll();
    }

    public UserEntity findById(Long id) {
        return UserEntity.findById(id);
    }

    public UserEntity findByEmail(String email) {
        return UserEntity.findByEmail(email).orElse(null);
    }

    @Transactional
    public UserEntity create(UserEntity user) {
        user.persist();
        return user;
    }

    @Transactional
    public UserEntity update(Long id, UserEntity userUpdate) {
        UserEntity entity = UserEntity.findById(id);

        if (entity == null) {
            return null;
        }

        if (userUpdate.email != null) {
            entity.email = userUpdate.email;
        }
        if (userUpdate.username != null) {
            entity.username = userUpdate.username;
        }

        return entity;
    }

    @Transactional
    public boolean delete(Long id) {
        UserEntity entity = UserEntity.findById(id);

        if (entity == null) {
            return false;
        }

        return UserEntity.deleteById(entity);
    }
}