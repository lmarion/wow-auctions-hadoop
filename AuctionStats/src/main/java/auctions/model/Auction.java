package auctions.model;

public class Auction 
{
	private String auc; //auctionid
	private String item; //itemid
	private String owner;
	private String ownerRealm;
	private long bid;
	private long buyout;
	private int quantity;
	private String timeLeft;
	private String rand;
	private String seed;
	private String petSpeciesId;
	private String petBreedId;
	private String petLevel;
	private String petQualityId;
	
	public String getAuc() {
		return auc;
	}
	public void setAuc(String auc) {
		this.auc = auc;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getOwnerRealm() {
		return ownerRealm;
	}
	public void setOwnerRealm(String ownerRealm) {
		this.ownerRealm = ownerRealm;
	}
	public long getBid() {
		return bid;
	}
	public void setBid(long bid) {
		this.bid = bid;
	}
	public long getBuyout() {
		return buyout;
	}
	public void setBuyout(long buyout) {
		this.buyout = buyout;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getTimeLeft() {
		return timeLeft;
	}
	public void setTimeLeft(String timeLeft) {
		this.timeLeft = timeLeft;
	}
	public String getRand() {
		return rand;
	}
	public void setRand(String rand) {
		this.rand = rand;
	}
	public String getSeed() {
		return seed;
	}
	public void setSeed(String seed) {
		this.seed = seed;
	}
	public String getPetSpeciesId() {
		return petSpeciesId;
	}
	public void setPetSpeciesId(String petSpeciesId) {
		this.petSpeciesId = petSpeciesId;
	}
	public String getPetBreedId() {
		return petBreedId;
	}
	public void setPetBreedId(String petBreedId) {
		this.petBreedId = petBreedId;
	}
	public String getPetLevel() {
		return petLevel;
	}
	public void setPetLevel(String petLevel) {
		this.petLevel = petLevel;
	}
	public String getPetQualityId() {
		return petQualityId;
	}
	public void setPetQualityId(String petQualityId) {
		this.petQualityId = petQualityId;
	}
}
