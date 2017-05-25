package auctions.model;


public class ServerAuctions 
{
	private Realm realm;
	private Alliance alliance;
	private Neutral neutral;
	private Horde horde;
	
	public Realm getRealm() {
		return realm;
	}
	public void setRealm(Realm realm) {
		this.realm = realm;
	}
	public Alliance getAlliance() {
		return alliance;
	}
	public void setAlliance(Alliance alliance) {
		this.alliance = alliance;
	}
	public Neutral getNeutral() {
		return neutral;
	}
	public void setNeutral(Neutral neutral) {
		this.neutral = neutral;
	}
	public Horde getHorde() {
		return horde;
	}
	public void setHorde(Horde horde) {
		this.horde = horde;
	}
}
