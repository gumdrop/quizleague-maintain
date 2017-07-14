package quizleague.data


import collection.mutable.Stack
import org.scalatest._
import com.google.appengine.tools.development.testing.LocalServiceTestHelper
import com.google.appengine.tools.development.testing.LocalTaskQueueTestConfig
import quizleague.domain._
import quizleague.data.Storage._
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
import com.google.appengine.api.datastore.DatastoreServiceFactory
import com.google.appengine.api.datastore._
import java.util.UUID.randomUUID
import java.util.ArrayList
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig
import org.threeten.bp._
import quizleague.util.json.codecs.DomainCodecs._
import quizleague.util.json.codecs.ScalaTimeCodecs._

class StorageSpec extends FlatSpec with Matchers with BeforeAndAfter with OptionValues {

//  "A Stack" should "pop values in last-in-first-out order" in {
//    val stack = new Stack[Int]
//    stack.push(1)
//    stack.push(2)
//    stack.pop() should be (2)
//    stack.pop() should be (1)
//  }
//
//  it should "throw NoSuchElementException if an empty stack is popped" in {
//    val emptyStack = new Stack[Int]
//    a [NoSuchElementException] should be thrownBy {
//      emptyStack.pop()
//    } 
//  }
  
  val helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig)
  
  val datastore = DatastoreServiceFactory.getDatastoreService()
  
  def uuid = randomUUID.toString()
  
  before{
    helper.setUp
  }
  
  after{
    helper.tearDown()
  }
  
  val venue = Venue(
      uuid, 
      "venue1","address1", 
      Some("phone1"),
      Some("email1"),
      Some("website1"),
      Some("imageURL1"))
      
  val team = Team(
        uuid, 
        "name1", 
        "shortName1", 
        Ref("venue",uuid), 
        Ref("text",uuid),
        List(Ref("user",uuid)))
        
  val result = Result(uuid, Ref("fixture", uuid), 23, 45, Option(Ref("user",uuid)),"note1" ,List(), true)
  
  val competition:Competition = LeagueCompetition(uuid, 
          "League", 
          LocalTime.of(20,30), 
          Duration.ofMinutes(90),
          List(Ref[Fixtures]("fixtures",uuid),Ref[Fixtures]("fixtures",uuid),Ref[Fixtures]("fixtures",uuid)),
          List(Ref[Results]("results",uuid),Ref[Results]("results",uuid)),
          List(Ref[LeagueTable]("leaguetable",uuid)),
          Ref[quizleague.domain.Text]("text",uuid),
          None)
  
  "Save" should "store a simple entity succesfully" in {
        
    val key = save(venue)
    
    
    val entity = datastore.get(key)
    
    entity.getProperty("id") should be (venue.id)
    entity.getProperty("name") should be (venue.name)
    entity.getProperty("address") should be (venue.address)
    entity.getProperty("phone") should be (venue.phone.value)
    
    val synthKey = KeyFactory.createKey("venue", venue.id)
    
    val entity2 = datastore.get(synthKey)
    
    entity2 should be (entity)
  
  }
  
  it should "store nested objects successfully" in {

    
    val key = save(team)
    
    val entity = datastore.get(key)
    
    entity.getProperty("venue").getClass should be (classOf[EmbeddedEntity]) 
    
    entity.getProperty("venue").asInstanceOf[EmbeddedEntity].getProperty("typeName") should be (team.venue.typeName)
    
    entity.getProperty("venue").asInstanceOf[EmbeddedEntity].getProperty("id") should be (team.venue.id)
    entity.getProperty("users") shouldBe a [ArrayList[_]]
    entity.getProperty("users").asInstanceOf[ArrayList[EmbeddedEntity]].iterator().next().getProperty("id") should be (team.users.iterator.next.id)
    
  }
  
   it should "store integer values successfully" in {

    
    val key = save(result)
    
    val entity = datastore.get(key)
    
    entity.getProperty("homeScore") should be (result.homeScore) 
    
   
  }
  
  it should "load stored objects successfully" in {
    save(venue)
    
    val v = load[Venue](venue.id)
    
    v should be (venue)
  }
  
  it should "load stored nested objects successfully" in {
    
    save(team)
    
    val t = load[Team](team.id)
    
    t should be (team)
    t.text should be (team.text)
  }
  
  it should "store and retrieve integer values successfully" in {
    
    save(result)
    
    val r = load[Result](result.id)
    
    r should be (result)
  }
  
  it should "retrieve all entities of a kind" in {
    save(venue)
    
    val venue1 = Venue(
      uuid, 
      "venue1","address1", 
      Some("phone1"),
      Some("email1"),
      Some("website1"),
      Some("imageURL1"))
    
    save(venue1)
    
      
    val v = list[Venue]
    
    v should have size (2)
    
    v should contain allOf (venue,venue1)
      
  }
  
  it should "save and load polymorphic entities successfully" in {
    
    save(competition)
    
    val c = load[Competition](competition.id)
    
    c shouldBe an[LeagueCompetition] 
  }
  
}