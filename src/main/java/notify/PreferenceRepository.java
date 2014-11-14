package notify;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
interface PreferenceRepository extends MongoRepository<Preference, String> {
}
