package Models;

/**
 * Created by ngpbh on 1/15/2017.
 */
public class Borrow {
    private Member currentMember;
    private Material currentMaterial;

    public Borrow(Member member, Material material) {
        this.currentMember = member;
        this.currentMaterial = material;
    }

    public Member getCurrentMember() {
        return currentMember;
    }

    public Material getCurrentMaterial() {
        return currentMaterial;
    }

}
