package com.course.Course.TopicController;

import com.course.Course.Entity.Topic;
import com.course.Course.Services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TopicController {

    @Autowired
        private TopicService topicService;

        @RequestMapping("/topics")
        public List<Topic> getAllTopics(){
            System.out.println("Get");
            return topicService.getAllTopics();
        }

        @RequestMapping("/topics/{id}")
        public Topic getTopicById(@PathVariable String id){
            return topicService.getTopicById(id);
        }

        @RequestMapping(value = "/topics", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
        public void addTopic(@RequestBody Topic topic){
            topicService.addTopic(topic);
        }

        @RequestMapping(value="/topics/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
        public void updateTopic(@RequestBody Topic topic, @PathVariable String id){
            System.out.println("Update");
            topicService.updateTopic(id, topic);
        }

        @RequestMapping(value="/topics/{id}", method = RequestMethod.DELETE)
        public void deleteById(@PathVariable("id") String id){
            topicService.deleteById(id);
        }

}
