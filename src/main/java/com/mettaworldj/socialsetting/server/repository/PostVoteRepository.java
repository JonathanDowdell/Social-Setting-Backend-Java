package com.mettaworldj.socialsetting.server.repository;

import com.mettaworldj.socialsetting.server.model.PostVoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostVoteRepository extends JpaRepository<PostVoteEntity, PostVoteEntity.PostVoteEntityId> {
}
