package notify;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
interface NotificationRepository extends MongoRepository<Notification, String> {
}
