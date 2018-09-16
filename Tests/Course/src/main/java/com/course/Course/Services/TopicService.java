package com.course.Course.Services;


import com.course.Course.Entity.Topic;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TopicService {

    private List<Topic> topics = new ArrayList<>(Arrays.asList(
            new Topic("hi","Joe", "Shmoe"),
            new Topic("hello","Loe", "Croe"),
            new Topic("by","Goe", "Broe")
    ));

    public List<Topic> getAllTopics(){
        return this.topics;
    }

    public Topic getTopicById(String id){
        /*for(Topic t : this.topics){
            if(t.getId().equals(id)){
                return t;
            }
        }
        return null;*/
        return topics.stream().filter(t -> t.getId().equals(id)).findFirst().get();
    }

    public void addTopic(Topic t){
        this.topics.add(t);
    }

    public void updateTopic(String id, Topic topic){
        //for(Topic t : this.topics){
        //    if(t.getId().equals(id)){
        //        t.setName(topic.getName());
        //        t.setDescription(topic.getDescription());
        //        return;
        //    }
        //}
        //System.out.println("Updating");
        //for(int i = 0;i<topics.size();i++){
        //    Topic t = topics.get(i);
        //    System.out.println(t.getId() + " is a matchh");
        //    if(t.getId().equals(id)){
        //        System.out.println(t.getId() + " is a matchh");
        //        topics.set(i,topic);
        //        return;
        //    }
        //}
        Topic t =  topics.stream().filter(tt -> tt.getId().equals(id)).findFirst().get();
        t.setName(topic.getName());
        t.setDescription(topic.getDescription());
    }

    public void deleteById(String id){
        //Topic t = this.topics.stream().filter(tt -> tt.getId().equals(id)).findFirst().get();
        //topics.remove(t);
        this.topics.removeIf(t -> t.getId().equals(id));
    }
}
