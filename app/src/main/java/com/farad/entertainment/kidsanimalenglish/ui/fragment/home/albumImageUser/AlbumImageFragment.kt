package com.farad.entertainment.kidsanimalenglish.ui.fragment.home.albumImageUser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.PagerSnapHelper
import com.farad.entertainment.kidsanimalenglish.base.BottomNavigationFragment
import com.farad.entertainment.kidsanimalenglish.databinding.FragmentAlbumBinding

class AlbumImageFragment : BottomNavigationFragment<FragmentAlbumBinding>() {

    private var listImage = ArrayList<Array<String>>()

    private val args by navArgs<AlbumImageFragmentArgs>()

    private val albumAdapter = AlbumAdapter()
    private val pagerSnapHelper by lazy { PagerSnapHelper() }
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentAlbumBinding
        get() = FragmentAlbumBinding::inflate

    override fun setup() {
        initImage()
        initViewPager()
    }

    private fun initViewPager() {
        val listImageUser = listImage[args.idAnimal].toMutableList()
        binding.carouselRecyclerview.adapter = albumAdapter
        albumAdapter.submitList(listImageUser)


        binding.carouselRecyclerview.setInfinite(true)
        binding.carouselRecyclerview.setFlat(true)
        pagerSnapHelper.attachToRecyclerView(binding.carouselRecyclerview)
        binding.CircleIndicator.attachToRecyclerView(binding.carouselRecyclerview, pagerSnapHelper)


    }

    private fun initImage() {
        //    "این آرایه 75 عضو داره و هر عضو به تعداد تصاویر اون حیوان عضو خواهد داشت"
        //    "کافیه شما لینک مربوط تصاویر هر حیوون رو به ترتیب در خانه های خالی قرار بدین"
       /* listImage.addAll(
            arrayOf(
                arrayOf(
                    "https://www.mediafire.com/file/98sel6xls3hfwh8/p1_1.jpg",
                    "https://www.mediafire.com/file/tgrsxkxmjt74xtn/p1_2.jpg",
                    "https://www.mediafire.com/file/laqkemnxzfw8wq5/p1_3.jpg",
                    "https://www.mediafire.com/file/wxkgxq9rrsnqyoe/p1_4.jpg",
                    "https://www.mediafire.com/file/nfilhsiotnllj72/p1_5.jpg",


                    ),
                arrayOf(
                    "https://www.mediafire.com/file/gwvyrat0emfb1oe/p2_1.jpg",
                    "https://www.mediafire.com/file/1zqey99ejrcg88c/p2_2.jpg",
                    "https://www.mediafire.com/file/0kw4wolj5pmk3bb/p2_3.jpg",
                    "https://www.mediafire.com/file/0s4f3ehao2j2hun/p2_4.jpg",
                    "https://www.mediafire.com/file/xpcluzpu0bmcmat/p2_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/sfxhlraf08loowy/p3_1.jpg",
                    "https://www.mediafire.com/file/3qi1vqcb35bh3iq/p3_2.jpg",
                    "https://www.mediafire.com/file/axfhodzk5g7rbwd/p3_3.jpg",
                    "https://www.mediafire.com/file/2vt65n0ioqz62qr/p3_4.jpg",
                    "https://www.mediafire.com/file/rp5kqfqncjfln3c/p3_5.jpg",

                    ),
                arrayOf(

                    "https://www.mediafire.com/file/ab4k1qvtodbcv5w/p4_1.jpg",
                    "https://www.mediafire.com/file/en62xq3nq7lsag6/p4_2.jpg",
                    "https://www.mediafire.com/file/mwpe90f77edacbn/p4_3.jpg",
                    "https://www.mediafire.com/file/n2vejlqg7l4ehem/p4_4.jpg",
                    "https://www.mediafire.com/file/hykbru8u4htq7om/p4_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/68qc2axdtlucldp/p5_1.jpg",
                    "https://www.mediafire.com/file/kiwn80q60jv6eok/p5_2.jpg",
                    "https://www.mediafire.com/file/e7rk6ivrfrqhoyu/p5_3.jpg",
                    "https://www.mediafire.com/file/7vysf99vrkz6bp6/p5_4.jpg",
                    "https://www.mediafire.com/file/zq4ooy8apbkkwcn/p5_5.jpg",
                ),
                arrayOf(
                    "https://www.mediafire.com/file/pu1k0i0iyt6vjgu/p6_1.jpg",
                    "https://www.mediafire.com/file/cw3sp71ybfy3rs6/p6_2.jpg",
                    "https://www.mediafire.com/file/eugpzrtwx3usn9e/p6_3.jpg",
                    "https://www.mediafire.com/file/xchcgirpsf8x0j9/p6_4.jpg",
                    "https://www.mediafire.com/file/14mrpeaddo94os3/p6_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/97p7cldhi38d716/p7_1.jpg",
                    "https://www.mediafire.com/file/mlag89fojqlwtnh/p7_2.jpg",
                    "https://www.mediafire.com/file/0p60p7lw4l038zm/p7_3.jpg",
                    "https://www.mediafire.com/file/ktfsxbabuq3l7yv/p7_4.jpg",
                    "https://www.mediafire.com/file/oacw0wv4ygpwe5z/p7_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/dbo2y7f70pj9qwm/p8_1.jpg",
                    "https://www.mediafire.com/file/jo0e7cbv2jyo0ls/p8_2.jpg",
                    "https://www.mediafire.com/file/zy3atzpvd8ak6uc/p8_3.jpg",
                    "https://www.mediafire.com/file/prchiul6v2yjlab/p8_4.jpg",
                    "https://www.mediafire.com/file/ckwl84a7anj5jc1/p8_5.jpg",
                ),
                arrayOf(
                    "https://www.mediafire.com/file/4xvzv5hc0m43x4e/p9_1.jpg",
                    "https://www.mediafire.com/file/wk6cq76r5helok3/p9_2.jpg",
                    "https://www.mediafire.com/file/swnvft939x5vcqd/p9_3.jpg",
                    "https://www.mediafire.com/file/t1eblzkaerh4820/p9_4.jpg",
                    "https://www.mediafire.com/file/twc496w7bf1au1x/p9_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/uj85tv61pfi3c99/p10_1.jpg",
                    "https://www.mediafire.com/file/z0ilot1cpbnebnt/p10_2.jpg",
                    "https://www.mediafire.com/file/uh0y5b4ygjwd66l/p10_3.jpg",
                    "https://www.mediafire.com/file/rzpihnta7swgfa0/p10_4.jpg",
                    "https://www.mediafire.com/file/kaapg8c23bqbbrn/p10_5.jpg",
                ),
                arrayOf(
                    "https://www.mediafire.com/file/5zc219482dk5f8p/p11_1.jpg",
                    "https://www.mediafire.com/file/w7iuhunb221n5um/p11_2.jpg",
                    "https://www.mediafire.com/file/6zsp5el95g83p2q/p11_3.jpg",
                    "https://www.mediafire.com/file/vmtygfz4j0oma9x/p11_4.jpg",
                    "https://www.mediafire.com/file/7zfsl8c69rr3czr/p11_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/0fxu6ejoi4xij4d/p12_1.jpg",
                    "https://www.mediafire.com/file/hscsgc1os0qvcsm/p12_2.jpg",
                    "https://www.mediafire.com/file/vkx3dk0ouy7k8su/p12_3.jpg",
                    "https://www.mediafire.com/file/fevhg7zemjrvn2w/p12_4.jpg",
                    "https://www.mediafire.com/file/ylvr1tugdt3alhq/p12_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/aiaebz1lroj2500/p13_1.jpg",
                    "https://www.mediafire.com/file/6c0374lb9c2dv2v/p13_2.jpg",
                    "https://www.mediafire.com/file/ozsiir4oj1rcv32/p13_3.jpg",
                    "https://www.mediafire.com/file/nzjs5tc5a27qae0/p13_4.jpg",
                    "https://www.mediafire.com/file/7tnpxltiz419e3n/p13_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/vu3ku2qx7jg8o5w/p14_1.jpg",
                    "https://www.mediafire.com/file/27n85szxfqffgky/p14_2.jpg",
                    "https://www.mediafire.com/file/eizawzvgwyji26v/p14_3.jpg",
                    "https://www.mediafire.com/file/rgsvyauk4zw01e0/p14_4.jpg",
                    "https://www.mediafire.com/file/vvqfigqczrxqmm2/p14_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/p2d85qfv8oi2wff/p15_1.jpg",
                    "https://www.mediafire.com/file/zrksxe9w335unqe/p15_2.jpg",
                    "https://www.mediafire.com/file/uurwi4oacgew2b5/p15_3.jpg",
                    "https://www.mediafire.com/file/zk8rp2nuri351sk/p15_4.jpg",
                    "https://www.mediafire.com/file/zjyk6bks2wgs6wt/p15_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/6t1m1a4h75ketx9/p16_1.jpg",
                    "https://www.mediafire.com/file/oasd15cz6htp03f/p16_2.jpg",
                    "https://www.mediafire.com/file/3ica1zlzdbsbkpb/p16_3.jpg",
                    "https://www.mediafire.com/file/60542h1qkau1k8g/p16_4.jpg",
                    "https://www.mediafire.com/file/bqfgeik9clgog0w/p16_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/zj3x4jpsfu0ahp9/p17_1.jpg",
                    "https://www.mediafire.com/file/uf0ac2qizyoxff7/p17_2.jpg",
                    "https://www.mediafire.com/file/lx52fwewzjxnn4u/p17_3.jpg",
                    "https://www.mediafire.com/file/tnhuow5kc2xag3s/p17_4.jpg",
                    "https://www.mediafire.com/file/djxt3h8wmc59gbn/p17_5.jpg",
                ),
                arrayOf(
                    "https://www.mediafire.com/file/u0o6j93g1u5q28q/p18_1.jpg",
                    "https://www.mediafire.com/file/7vn2f4jzyqldmh8/p18_2.jpg",
                    "https://www.mediafire.com/file/0kahx3iejk0xgna/p18_3.jpg",
                    "https://www.mediafire.com/file/tizd90futrphd60/p18_4.jpg",
                    "https://www.mediafire.com/file/v0u9ce1xfx86ynz/p18_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/b5o5sbl47j70w8t/p19_1.jpg",
                    "https://www.mediafire.com/file/kbgnpkwgqo3lo36/p19_2.jpg",
                    "https://www.mediafire.com/file/x74pr44qunpsruq/p19_3.jpg",
                    "https://www.mediafire.com/file/f9vj1anxpd2sa6j/p19_4.jpg",
                    "https://www.mediafire.com/file/8nv6fcabbg4xalr/p19_5.jpg",
                ),
                arrayOf(
                    "https://www.mediafire.com/file/5aczi655rvom332/p20_1.jpg",
                    "https://www.mediafire.com/file/5uk6f3pdwm4rrxq/p20_2.jpg",
                    "https://www.mediafire.com/file/lmihecd7es8fo8r/p20_3.jpg",
                    "https://www.mediafire.com/file/qe5vlqrs9bik6y0/p20_4.jpg",
                    "https://www.mediafire.com/file/1ebcjlwm0fej4bl/p20_5.jpg"
                ),
                arrayOf(
                    "https://www.mediafire.com/file/i1b8jvmcf78081b/p21_1.jpg",
                    "https://www.mediafire.com/file/yp706hyub2nbz06/p21_2.jpg",
                    "https://www.mediafire.com/file/xkjpih4ef2djw89/p21_3.jpg",
                    "https://www.mediafire.com/file/pv4yuwy369g3ko6/p21_4.jpg",
                    "https://www.mediafire.com/file/iuwrbzuklnkvcnp/p21_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/7p0pqx1los8ox5x/p22_1.jpg",
                    "https://www.mediafire.com/file/jtvxq9wyz8aae57/p22_2.jpg",
                    "https://www.mediafire.com/file/cj5oet90r7siopj/p22_3.jpg",
                    "https://www.mediafire.com/file/k0aoa00eaibpbl0/p22_4.jpg",
                    "https://www.mediafire.com/file/iau04czu3i14e44/p22_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/16ui1tcvqhcug7e/p23_1.jpg",
                    "https://www.mediafire.com/file/wkxgrdskflqpcwh/p23_2.jpg",
                    "https://www.mediafire.com/file/nfcolrr62rpaf0y/p23_3.jpg",
                    "https://www.mediafire.com/file/laogut69ut7inmv/p23_4.jpg",
                    "https://www.mediafire.com/file/hjhm8o4ft49oekq/p23_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/504ovtg65jgm2wb/p24_1.jpg",
                    "https://www.mediafire.com/file/mn0g6js0pgsobwl/p24_2.jpg",
                    "https://www.mediafire.com/file/vvrfn3yj16fteig/p24_3.jpg",
                    "https://www.mediafire.com/file/d6e3n3o7um1em50/p24_4.jpg",
                    "https://www.mediafire.com/file/38b8qmqflpplt3d/p24_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/8cp7t69kwkx1sd0/p25_1.jpg",
                    "https://www.mediafire.com/file/ibbh0qkofdigtnz/p25_2.jpg",
                    "https://www.mediafire.com/file/6by3gvwrlwmv5hy/p25_3.jpg",
                    "https://www.mediafire.com/file/5yjrknx9umdvs2b/p25_4.jpg",
                    "https://www.mediafire.com/file/6et9n3nxao8ei9v/p25_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/8dzrj4zho9sa7vk/p26_1.jpg",
                    "https://www.mediafire.com/file/oid45gh918uo6zt/p26_2.jpg",
                    "https://www.mediafire.com/file/v5b52gfe0m77fo3/p26_3.jpg",
                    "https://www.mediafire.com/file/fpeey9ykc5l4f6q/p26_4.jpg",
                    "https://www.mediafire.com/file/tjbs1kt0gp479lu/p26_5.jpg"
                ),
                arrayOf(
                    "https://www.mediafire.com/file/i6hmxzxybqh7z7q/p27_1.jpg",
                    "https://www.mediafire.com/file/s6jtw2ztz50smld/p27_2.jpg",
                    "https://www.mediafire.com/file/jcutl6mkyx1oc56/p27_3.jpg",
                    "https://www.mediafire.com/file/1oo2t3cfshv1jyc/p27_4.jpg",
                    "https://www.mediafire.com/file/bndhz3u08ogbz0w/p27_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/wiwq8dhsw8bq1yy/p28_1.jpg",
                    "https://www.mediafire.com/file/1atps8cscqskfu1/p28_2.jpg",
                    "https://www.mediafire.com/file/9wdy5opgbd56s3n/p28_3.jpg",
                    "https://www.mediafire.com/file/ksx0nneve8p21yo/p28_4.jpg",
                    "https://www.mediafire.com/file/78ggs1w8w783ch0/p28_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/uq2rp0w3346riw8/p29_1.jpg",
                    "https://www.mediafire.com/file/dv5a7qcjw3w83xi/p29_2.jpg",
                    "https://www.mediafire.com/file/3flss2vr3zkwsbd/p29_3.jpg",
                    "https://www.mediafire.com/file/ghjc0q7d7xgmvfl/p29_4.jpg",
                    "https://www.mediafire.com/file/s76yepa3qcvqqnk/p29_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/29fhk4laz73219h/p30_1.jpg",
                    "https://www.mediafire.com/file/qnkfmmhllzc4to6/p30_2.jpg",
                    "https://www.mediafire.com/file/j89pv23z5j0q84a/p30_3.jpg",
                    "https://www.mediafire.com/file/ubwa4zw6fjn7f0p/p30_4.jpg",
                    "https://www.mediafire.com/file/ha0frnh5i7qqomu/p30_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/hea1kejftxjbiv3/p31_1.jpg",
                    "https://www.mediafire.com/file/k8z2xl4xohd2des/p31_2.jpg",
                    "https://www.mediafire.com/file/4xrl5bk3lh0qpht/p31_3.jpg",
                    "https://www.mediafire.com/file/4f94mvu8yg5heal/p31_4.jpg",
                    "https://www.mediafire.com/file/z1bxrsrxeumubev/p31_5.jpg",
                ),
                arrayOf(
                    "https://www.mediafire.com/file/16xm965wtkscjfr/p32_1.jpg",
                    "https://www.mediafire.com/file/ieogs6po5uktnk2/p32_2.jpg",
                    "https://www.mediafire.com/file/jzw87wfhmz6tvbd/p32_3.jpg",
                    "https://www.mediafire.com/file/916wso2e1723oqp/p32_4.jpg",
                    "https://www.mediafire.com/file/x2kxsqo6nrxwmgq/p32_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/on2bb4xwyrzmgie/p33_1.jpg",
                    "https://www.mediafire.com/file/ypinnhldap523jx/p33_2.jpg",
                    "https://www.mediafire.com/file/omybrhir8u4ccm4/p33_3.jpg",
                    "https://www.mediafire.com/file/ly9jn3sqiwkt7jj/p33_4.jpg",
                    "https://www.mediafire.com/file/9usq14va0gegwev/p33_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/qlv8s5mnifwctxi/p34_1.jpg",
                    "https://www.mediafire.com/file/gj3qn7fbxqkqawm/p34_2.jpg",
                    "https://www.mediafire.com/file/fj9qn4vdwfa06vu/p34_3.jpg",
                    "https://www.mediafire.com/file/sfs5qftcryt2v7u/p34_4.jpg",
                    "https://www.mediafire.com/file/2y40rvpgca6d2pm/p34_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/3046bbgqb2yj34o/p35_1.jpg",
                    "https://www.mediafire.com/file/q20i3ku41gcyxd0/p35_2.jpg",
                    "https://www.mediafire.com/file/d1ia6bgdz6m61zg/p35_3.jpg",
                    "https://www.mediafire.com/file/r0lzedhqnwv47gz/p35_4.jpg",
                    "https://www.mediafire.com/file/dsvrastm1cikiik/p35_5.jpg",
                ),
                arrayOf(
                    "https://www.mediafire.com/file/0lnxjrigife7kv0/p36_1.jpg",
                    "https://www.mediafire.com/file/raiqbdzzceoytcx/p36_2.jpg",
                    "https://www.mediafire.com/file/hoqoqitbwu88kh6/p36_3.jpg",
                    "https://www.mediafire.com/file/2jedlziv2ayww8q/p36_4.jpg",
                    "https://www.mediafire.com/file/9jbbpneopu8k0zf/p36_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/o08b9llepebq2wx/p37_1.jpg",
                    "https://www.mediafire.com/file/bevnuqpvagu3a5k/p37_2.jpg",
                    "https://www.mediafire.com/file/knovzrxllkasxqz/p37_3.jpg",
                    "https://www.mediafire.com/file/sf5bmwb9kpjc2ui/p37_4.jpg",
                    "https://www.mediafire.com/file/qx208wg987dyyen/p37_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/j4pvi65sipdbvpm/p38_1.jpg",
                    "https://www.mediafire.com/file/qa3n0058urnuvyx/p38_2.jpg",
                    "https://www.mediafire.com/file/z67xmtuboopjax0/p38_3.jpg",
                    "https://www.mediafire.com/file/rkybtctocl4jpv7/p38_4.jpg",
                    "https://www.mediafire.com/file/pqknrpqbmvwhozj/p38_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/vq9e7zq8ixw1qdw/p39_1.jpg",
                    "https://www.mediafire.com/file/t1ru2lv3crvt3pv/p39_2.jpg",
                    "https://www.mediafire.com/file/xdj0ak986mp0t4g/p39_3.jpg",
                    "https://www.mediafire.com/file/rq7n1n20zlyqsre/p39_4.jpg",
                    "https://www.mediafire.com/file/49pjih0jz99h0tq/p39_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/ojysa20shan0h4y/p40_1.jpg",
                    "https://www.mediafire.com/file/cadtskp29ypt206/p40_2.jpg",
                    "https://www.mediafire.com/file/8jpthfyvl2nekqj/p40_3.jpg",
                    "https://www.mediafire.com/file/uuzxm5q3zh9i2tg/p40_4.jpg",
                    "https://www.mediafire.com/file/kbhj0m1ffj53i0x/p40_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/ktkyt2rocush2bu/p41_1.jpg",
                    "https://www.mediafire.com/file/4j7r4hqef0hlh3x/p41_2.jpg",
                    "https://www.mediafire.com/file/ye5zequxkyzy86y/p41_3.jpg",
                    "https://www.mediafire.com/file/xsjs50shf2h3zcz/p41_4.jpg",
                    "https://www.mediafire.com/file/34uf7tfkwncmufy/p41_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/irqt4o7dcpmmtr6/p42_1.jpg",
                    "https://www.mediafire.com/file/irqt4o7dcpmmtr6/p42_1.jpg",
                    "https://www.mediafire.com/file/yvebsrclk0lim9z/p42_3.jpg",
                    "https://www.mediafire.com/file/i9ri7jv1bhkmjhh/p42_4.jpg",
                    "https://www.mediafire.com/file/ny302tbyshjq4kp/p42_5.jpg"
                ),
                arrayOf(
                    "https://www.mediafire.com/file/ideexyt2qpnwvbj/p43_1.jpg",
                    "https://www.mediafire.com/file/ko1uetihz8ublkb/p43_2.jpg",
                    "https://www.mediafire.com/file/uovdfsp68smit8e/p43_3.jpg",
                    "https://www.mediafire.com/file/9y83x9bli5lxwdu/p43_4.jpg",
                    "https://www.mediafire.com/file/crnt1es2suxkmcn/p43_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/twap43inldsritp/p44_1.jpg",
                    "https://www.mediafire.com/file/5gih089j8lx2kz2/p44_2.jpg",
                    "https://www.mediafire.com/file/jfeje8thtqk8y7c/p44_3.jpg",
                    "https://www.mediafire.com/file/rd7uu97wgk375hl/p44_4.jpg",
                    "https://www.mediafire.com/file/fq7qbl3a1umu49t/p44_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/sjovviw10oppkkg/p45_1.jpg",
                    "https://www.mediafire.com/file/apa0vyc40uswbjq/p45_2.jpg",
                    "https://www.mediafire.com/file/ltxesy4rciz1ac1/p45_3.jpg",
                    "https://www.mediafire.com/file/iu7iwn2z5uiks63/p45_4.jpg",
                    "https://www.mediafire.com/file/hsygb1k6ik4z33l/p45_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/tvq1zib4xy1w4zh/p46_1.jpg",
                    "https://www.mediafire.com/file/cejh3bekyypf5jt/p46_2.jpg",
                    "https://www.mediafire.com/file/vtppbt66nc57nf3/p46_3.jpg",
                    "https://www.mediafire.com/file/nbbl9m5t8ij6gc4/p46_4.jpg",
                    "https://www.mediafire.com/file/3n2egs76xk8y161/p46_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/nmfzzjseo9rx27i/p47_1.jpg",
                    "https://www.mediafire.com/file/k8zkt1wtpgy8yfu/p47_2.jpg",
                    "https://www.mediafire.com/file/p3i53lenpv5aakk/p47_3.jpg",
                    "https://www.mediafire.com/file/yxgsibah2v79v4w/p47_4.jpg",
                    "https://www.mediafire.com/file/dfctgblar66xny3/p47_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/91bsasn7q6ocsi9/p48_1.jpg",
                    "https://www.mediafire.com/file/ehcp8kuoajwlsmt/p48_2.jpg",
                    "https://www.mediafire.com/file/lawbm2k48suzr38/p48_3.jpg",
                    "https://www.mediafire.com/file/1dg85x2gn6cp50n/p48_4.jpg",
                    "https://www.mediafire.com/file/52mk5enens9exjk/p48_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/02wji37ntx6rf83/p49_1.jpg",
                    "https://www.mediafire.com/file/owubsvwpivdmoig/p49_2.jpg",
                    "https://www.mediafire.com/file/o3hfxolzg3gpv83/p49_3.jpg",
                    "https://www.mediafire.com/file/f7z85kq339xabkz/p49_4.jpg",
                    "https://www.mediafire.com/file/zrtacotcofiyel2/p49_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/041r9537k8idla3/p50_1.jpg",
                    "https://www.mediafire.com/file/v5voukhcef20306/p50_2.jpg",
                    "https://www.mediafire.com/file/iultl1v0twpwzw4/p50_3.jpg",
                    "https://www.mediafire.com/file/6kno5czsxujq2dn/p50_4.jpg",
                    "https://www.mediafire.com/file/ojbuft2rd1pwqqf/p50_5.jpg"
                ),
                arrayOf(
                    "https://www.mediafire.com/file/vl96ll0s1rxfkfa/p51_1.jpg",
                    "https://www.mediafire.com/file/rrxk59x33ngx0xp/p51_2.jpg",
                    "https://www.mediafire.com/file/6710jib2zwbolct/p51_3.jpg",
                    "https://www.mediafire.com/file/vscfkf6tnps8n0i/p51_4.jpg",
                    "https://www.mediafire.com/file/ecagtqe44fu0p9j/p51_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/twqcj6k8h7ghflr/p52_1.jpg",
                    "https://www.mediafire.com/file/578hk7s8fn4z9gq/p52_2.jpg",
                    "https://www.mediafire.com/file/j0temk42tyr46xa/p52_3.jpg",
                    "https://www.mediafire.com/file/xqcvnltx1awfn07/p52_4.jpg",
                    "https://www.mediafire.com/file/9jvxhvr8qqxlp09/p52_5.jpg"
                ),
                arrayOf(
                    "https://www.mediafire.com/file/ljozix2o3fgrgx3/p53_1.jpg",
                    "https://www.mediafire.com/file/bzvm6jel63fppk8/p53_2.jpg",
                    "https://www.mediafire.com/file/evpagmjastl1gxt/p53_3.jpg",
                    "https://www.mediafire.com/file/laryjvqh7ngx6bj/p53_4.jpg",
                    "https://www.mediafire.com/file/jr3pezdlo86qqvg/p53_5.jpg",
                ),
                arrayOf(
                    "https://www.mediafire.com/file/p89hgr1anx9wdrq/p54_1.jpg",
                    "https://www.mediafire.com/file/qwmspwehys8m67q/p54_2.jpg",
                    "https://www.mediafire.com/file/w2hvztztp86r8ry/p54_3.jpg",
                    "https://www.mediafire.com/file/wxz92csgz4p45hy/p54_4.jpg",
                    "https://www.mediafire.com/file/0mslstpnddr5wbh/p54_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/tkc8eu4ugcror8p/p55_1.jpg",
                    "https://www.mediafire.com/file/n7bdc4nsmwnnmet/p55_2.jpg",
                    "https://www.mediafire.com/file/9c7hi8waz816j3d/p55_3.jpg",
                    "https://www.mediafire.com/file/lnfv96cms2tmi1l/p55_4.jpg",
                    "https://www.mediafire.com/file/xlu191z045fpn9e/p55_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/25ekk6i7qmavw99/p56_1.jpg",
                    "https://www.mediafire.com/file/af008daw3xoi4yj/p56_2.jpg",
                    "https://www.mediafire.com/file/snmb0j2lithjeiw/p56_3.jpg",
                    "https://www.mediafire.com/file/e0h1vr6mlzljykn/p56_4.jpg",
                    "https://www.mediafire.com/file/wv5bv3nsewku5bc/p56_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/duy017kpvi64pam/p57_1.jpg",
                    "https://www.mediafire.com/file/fq5pxsy0tl2zsd5/p57_2.jpg",
                    "https://www.mediafire.com/file/k8v0l3a288vky2u/p57_3.jpg",
                    "https://www.mediafire.com/file/ryxb9iyksyxwfb8/p57_4.jpg",
                    "https://www.mediafire.com/file/v967silhl4our0t/p57_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/oqlf4d5ve64xy3o/p58_1.jpg",
                    "https://www.mediafire.com/file/mz44n87535j9kya/p58_2.jpg",
                    "https://www.mediafire.com/file/tz3tcn3akmf1xt1/p58_3.jpg",
                    "https://www.mediafire.com/file/86wjnznpcs14hwi/p58_4.jpg",
                    "https://www.mediafire.com/file/203bjbgdbckr37c/p58_5.jpg"
                ),
                arrayOf(
                    "https://www.mediafire.com/file/9yrt5gc7lpvxx20/p59_1.jpg",
                    "https://www.mediafire.com/file/ll364dfm6t10w8y/p59_2.jpg",
                    "https://www.mediafire.com/file/6x65cmo7enjefp8/p59_3.jpg",
                    "https://www.mediafire.com/file/6pnx754331rfvpx/p59_4.jpg",
                    "https://www.mediafire.com/file/kdm6lfvve87oevy/p59_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/79hgj91oe1haxhr/p60_1.jpg",
                    "https://www.mediafire.com/file/952dzk5yhwra6ow/p60_2.jpg",
                    "https://www.mediafire.com/file/k447ca41uum5gqr/p60_3.jpg",
                    "https://www.mediafire.com/file/bvsau99ogg0o3dx/p60_4.jpg",
                    "https://www.mediafire.com/file/n9szmfqnj1p0f5m/p60_5.jpg"
                ),
                arrayOf(
                    "https://www.mediafire.com/file/cg9ornhn9greimi/p61_1.jpg",
                    "https://www.mediafire.com/file/nxgqmmkja30xfs1/p61_2.jpg",
                    "https://www.mediafire.com/file/kigbco9q72zy34h/p61_3.jpg",
                    "https://www.mediafire.com/file/xuujoup8sbcr4rx/p61_4.jpg",
                    "https://www.mediafire.com/file/om401cbyq0htrfr/p61_5.jpg",
                ),
                arrayOf(
                    "https://www.mediafire.com/file/pyg7yxbyi1nj1cm/p62_1.jpg",
                    "https://www.mediafire.com/file/fwfvwyq8limxukh/p62_2.jpg",
                    "https://www.mediafire.com/file/xl9217lotzowldj/p62_3.jpg",
                    "https://www.mediafire.com/file/0nuz7aom3orzf94/p62_4.jpg",
                    "https://www.mediafire.com/file/obsubxix8pl2h5j/p62_5.jpg",
                ),
                arrayOf(
                    "https://www.mediafire.com/file/3qpsp1auc5xbww7/p63_1.jpg",
                    "https://www.mediafire.com/file/lm935xxjuw0th55/p63_2.jpg",
                    "https://www.mediafire.com/file/uhj2o4idn1o5adb/p63_3.jpg",
                    "https://www.mediafire.com/file/ueca5ji9jx4828l/p63_4.jpg",
                    "https://www.mediafire.com/file/w079lq42sj9amih/p63_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/pfwwp57o0snom08/p64_1.jpg",
                    "https://www.mediafire.com/file/qcdcy4w6xmylehj/p64_2.jpg",
                    "https://www.mediafire.com/file/9cw2hxsaxu3qixo/p64_3.jpg",
                    "https://www.mediafire.com/file/57wi18y7vuysr6f/p64_4.jpg",
                    "https://www.mediafire.com/file/yl32fqbw0i53w1x/p64_5.jpg"
                ),
                arrayOf(
                    "https://www.mediafire.com/file/5z0ftgnnim7oe9g/p65_1.jpg",
                    "https://www.mediafire.com/file/ctx69hch6v7xb6b/p65_2.jpg",
                    "https://www.mediafire.com/file/ayhe5o6mjo8l49q/p65_3.jpg",
                    "https://www.mediafire.com/file/byderjkfdu253y8/p65_4.jpg",
                    "https://www.mediafire.com/file/b3lqkglpf3r0ija/p65_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/5haia7kulnqqci7/p66_1.jpg",
                    "https://www.mediafire.com/file/gtbep7rma3k3na8/p66_2.jpg",
                    "https://www.mediafire.com/file/u1neq6wax93f51w/p66_3.jpg",
                    "https://www.mediafire.com/file/fu8g5sjat43tny3/p66_4.jpg",
                    "https://www.mediafire.com/file/vxq1s2baywh7327/p66_5.jpg",
                ),
                arrayOf(
                    "https://www.mediafire.com/file/u5859avnj4iuof1/p67_1.jpg",
                    "https://www.mediafire.com/file/dkkl7kwu45ua8if/p67_2.jpg",
                    "https://www.mediafire.com/file/nrfa6euzsux79x7/p67_3.jpg",
                    "https://www.mediafire.com/file/nr2x7yxy57k3pen/p67_4.jpg",
                    "https://www.mediafire.com/file/l8vgvyx0frdzlz6/p67_5.jpg",
                ),
                arrayOf(
                    "https://www.mediafire.com/file/11e3t08fw6nt96b/p68_1.jpg",
                    "https://www.mediafire.com/file/yev3apb6z2eglhx/p68_2.jpg",
                    "https://www.mediafire.com/file/b9zft9ppzl44sks/p68_3.jpg",
                    "https://www.mediafire.com/file/fgahh16nx58ox6i/p68_4.jpg",
                    "https://www.mediafire.com/file/njx7lh8e55587dj/p68_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/ouj4kludi1jnab6/p69_1.jpg",
                    "https://www.mediafire.com/file/hcftwcpnb6p2s3h/p69_2.jpg",
                    "https://www.mediafire.com/file/6g4ql1hqgb854do/p69_3.jpg",
                    "https://www.mediafire.com/file/czz6s9neni2zg81/p69_4.jpg",
                    "https://www.mediafire.com/file/st0p2m1rjbndt2m/p69_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/7h7tqrmvnxiecs9/p70_1.jpg",
                    "https://www.mediafire.com/file/wsllt2btdnj6icd/p70_2.jpg",
                    "https://www.mediafire.com/file/m8scws4dzwxdncd/p70_3.jpg",
                    "https://www.mediafire.com/file/lpv0orsrttsc4c6/p70_4.jpg",
                    "https://www.mediafire.com/file/isn8glitwj9doxn/p70_5.jpg",

                    ),
                arrayOf(
                    "https://www.mediafire.com/file/km8df5a9ydxcthc/p71_1.jpg",
                    "https://www.mediafire.com/file/wp76o6w4ci6xs3l/p71_2.jpg",
                    "https://www.mediafire.com/file/hh0eqknw5b5uj24/p71_3.jpg",
                    "https://www.mediafire.com/file/o4ej8gqgp5ekjdy/p71_4.jpg",
                    "https://www.mediafire.com/file/iolg7m3wjsuo739/p71_5.jpg",
                ),
                arrayOf(
                    "https://www.mediafire.com/file/xdwc6h90tio8qv1/p72_1.jpg",
                    "https://www.mediafire.com/file/7838ahqoxzhh0c3/p72_2.jpg",
                    "https://www.mediafire.com/file/wlztsgt8p7fqhip/p72_3.jpg",
                    "https://www.mediafire.com/file/ky24fzomlkymllv/p72_4.jpg",
                    "https://www.mediafire.com/file/m2z4x0xv95dd49z/p72_5.jpg",
                ),
                arrayOf(
                    "https://www.mediafire.com/file/rhtk3gr1nbu44xf/p73_1.jpg",
                    "https://www.mediafire.com/file/udedtim9b674upp/p73_2.jpg",
                    "https://www.mediafire.com/file/ei09scrsp4or6gv/p73_3.jpg",
                    "https://www.mediafire.com/file/utl5nt5e26pvf28/p73_4.jpg",
                    "https://www.mediafire.com/file/aihmgdpokwct1so/p73_5.jpg",
                ),
                arrayOf(
                    "https://www.mediafire.com/file/2bckee38c84108m/p74_1.jpg",
                    "https://www.mediafire.com/file/5bxx2z8m0wd3fx3/p74_2.jpg",
                    "https://www.mediafire.com/file/twypca3fb0jgu8k/p74_3.jpg",
                    "https://www.mediafire.com/file/ltak9q3h31oy28c/p74_4.jpg",
                    "https://www.mediafire.com/file/bjwyzebu53qz31p/p74_5.jpg"
                ),
                arrayOf(
                    "https://www.mediafire.com/file/w8vc850wlfozy09/p75_1.jpg",
                    "https://www.mediafire.com/file/qd7u721cpzaampg/p75_2.jpg",
                    "https://www.mediafire.com/file/v9texv4sh6qn0wn/p75_3.jpg",
                    "https://www.mediafire.com/file/skdhqktmwdzpetj/p75_4.jpg",
                    "https://www.mediafire.com/file/gb0jegz04t6kjvu/p75_5.jpg"
                )
            )
        )*/

        //                public static String array3D[][][]=
        //                        {{"","",""}};
        //    "این آرایه 75 عضو داره و هر عضو به تعداد تصاویر اون حیوان عضو خواهد داشت"
        //    "کافیه شما لینک مربوط تصاویر هر حیوون رو به ترتیب در خانه های خالی قرار بدین"

        listImage.addAll( arrayOf(
            arrayOf(

                "https://s6.picofile.com/file/8375503692/p1_1.jpg",
                "https://s6.picofile.com/file/8375503750/p1_2.jpg",
                "https://s7.picofile.com/file/8375503792/p1_3.jpg",
                "https://s6.picofile.com/file/8375503818/p1_4.jpg",
                "https://s6.picofile.com/file/8375503850/p1_5.jpg"
            ),  //0
            arrayOf(

                "https://s7.picofile.com/file/8375503918/p2_1.jpg",
                "https://s6.picofile.com/file/8375503934/p2_2.jpg",
                "https://s6.picofile.com/file/8375503968/p2_3.jpg",
                "https://s6.picofile.com/file/8375503984/p2_4.jpg",
                "https://s7.picofile.com/file/8375504034/p2_5.jpg",

                ),  // 1
            arrayOf(

                "https://s6.picofile.com/file/8375508726/p3_1.jpg",
                "https://s6.picofile.com/file/8375508734/p3_2.jpg",
                "https://s7.picofile.com/file/8375508768/p3_3.jpg",
                "https://s7.picofile.com/file/8375508784/p3_4.jpg",
                "https://s6.picofile.com/file/8375509034/p3_5.jpg",
                "https://s7.picofile.com/file/8382167518/p3_6.jpg"
            ),  // 2
            arrayOf(

                "https://s6.picofile.com/file/8375510300/p4_1.jpg",
                "https://s6.picofile.com/file/8375510318/p4_2.jpg",
                "https://s6.picofile.com/file/8375510326/p4_3.jpg",
                "https://s6.picofile.com/file/8375510334/p4_4.jpg",
                "https://s7.picofile.com/file/8375510342/p4_5.jpg"
            ),  // 3
            arrayOf(

                "https://s7.picofile.com/file/8375510376/p5_1.jpg",
                "https://s7.picofile.com/file/8375510400/p5_2.jpg",
                "https://s7.picofile.com/file/8375510434/p5_3.jpg",
                "https://s7.picofile.com/file/8375510442/p5_4.jpg",
                "https://s7.picofile.com/file/8375510450/p5_5.jpg"
            ),  // 4
            arrayOf(

                "https://s6.picofile.com/file/8375510476/p6_1.jpg",
                "https://s6.picofile.com/file/8375510484/p6_2.jpg",
                "https://s7.picofile.com/file/8375510492/p6_3.jpg",
                "https://s7.picofile.com/file/8375510500/p6_4.jpg",
                "https://s6.picofile.com/file/8375510518/p6_5.jpg"
            ),  // 5
            arrayOf(

                "https://s7.picofile.com/file/8375510526/p7_1.jpg",
                "https://s7.picofile.com/file/8375510534/p7_2.jpg",
                "https://s7.picofile.com/file/8375510550/p7_3.jpg",
                "https://s7.picofile.com/file/8375510568/p7_4.jpg",
                "https://s6.picofile.com/file/8375510576/p7_5.jpg"
            ),  // 6
            arrayOf(

                "https://s6.picofile.com/file/8375510584/p8_1.jpg",
                "https://s7.picofile.com/file/8375510592/p8_2.jpg",
                "https://s7.picofile.com/file/8375510600/p8_3.jpg",
                "https://s6.picofile.com/file/8375510700/p8_4.jpg",
                "https://s7.picofile.com/file/8375510726/p8_5.jpg",
                "https://s6.picofile.com/file/8382167642/p8_6.jpg"
            ),  // 7
            arrayOf(

                "https://s6.picofile.com/file/8375510742/p9_1.jpg",
                "https://s6.picofile.com/file/8375510768/p9_2.jpg",
                "https://s7.picofile.com/file/8375510784/p9_3.jpg",
                "https://s7.picofile.com/file/8375510792/p9_4.jpg",
                "https://s7.picofile.com/file/8375510800/p9_5.jpg"
            ),  // 8
            arrayOf(

                "https://s7.picofile.com/file/8375510818/p10_1.jpg",
                "https://s6.picofile.com/file/8375510826/p10_2.jpg",
                "https://s7.picofile.com/file/8375510834/p10_3.jpg",
                "https://s7.picofile.com/file/8375510842/p10_4.jpg",
                "https://s7.picofile.com/file/8375510868/p10_5.jpg"
            ),  // 9
            arrayOf(
                "https://s6.picofile.com/file/8375510876/p11_1.jpg",
                "https://s7.picofile.com/file/8375510892/p11_2.jpg",
                "https://s7.picofile.com/file/8375510900/p11_3.jpg",
                "https://s7.picofile.com/file/8375510918/p11_4.jpg",
                "https://s6.picofile.com/file/8375510926/p11_5.jpg"
            ),  // 10
            arrayOf(

                "https://s6.picofile.com/file/8375510934/p12_1.jpg",
                "https://s7.picofile.com/file/8375510942/p12_2.jpg",
                "https://s7.picofile.com/file/8375510950/p12_3.jpg",
                "https://s6.picofile.com/file/8375510976/p12_4.jpg",
                "https://s6.picofile.com/file/8375510984/p12_5.jpg",
                "https://s7.picofile.com/file/8382167700/p12_6.jpg"
            ),  //11
            arrayOf(

                "https://s6.picofile.com/file/8375510992/p13_1.jpg",
                "https://s6.picofile.com/file/8375511000/p13_2.jpg",
                "https://s6.picofile.com/file/8375511026/p13_3.jpg",
                "https://s6.picofile.com/file/8375511034/p13_4.jpg",
                "https://s6.picofile.com/file/8375511050/p13_5.jpg"
            ),  // 12
            arrayOf(

                "https://s6.picofile.com/file/8375511068/p14_1.jpg",
                "https://s7.picofile.com/file/8375511076/p14_2.jpg",
                "https://s7.picofile.com/file/8375511084/p14_3.jpg",
                "https://s7.picofile.com/file/8375511092/p14_4.jpg",
                "https://s6.picofile.com/file/8375511100/p14_5.jpg",
                "https://s3.uupload.ir/files/mrghooghooli/mrghooghooli/user_pic/p14_100.jpg"
            ),  //13
            arrayOf(

                "https://s7.picofile.com/file/8375511118/p15_1.jpg",
                "https://s6.picofile.com/file/8375511126/p15_2.jpg",
                "https://s7.picofile.com/file/8375511134/p15_3.jpg",
                "https://s7.picofile.com/file/8375511142/p15_4.jpg",
                "https://s6.picofile.com/file/8375511176/p15_5.jpg"
            ),  //14
            arrayOf(

                "https://s7.picofile.com/file/8375511184/p16_1.jpg",
                "https://s6.picofile.com/file/8375511192/p16_2.jpg",
                "https://s7.picofile.com/file/8375511218/p16_3.jpg",
                "https://s7.picofile.com/file/8375511226/p16_4.jpg",
                "https://s6.picofile.com/file/8375511234/p16_5.jpg"
            ),  // 15
            arrayOf(
                "https://s6.picofile.com/file/8375511268/p17_1.jpg",
                "https://s6.picofile.com/file/8375511276/p17_2.jpg",
                "https://s6.picofile.com/file/8375511284/p17_3.jpg",
                "https://s7.picofile.com/file/8375511292/p17_4.jpg",
                "https://s7.picofile.com/file/8375511300/p17_5.jpg",
                "https://s6.picofile.com/file/8382167750/p17_6.jpg"
            ),  //16
            arrayOf(

                "https://s6.picofile.com/file/8375511326/p18_1.jpg",
                "https://s7.picofile.com/file/8375511334/p18_2.jpg",
                "https://s7.picofile.com/file/8375511342/p18_3.jpg",
                "https://s7.picofile.com/file/8375511350/p18_4.jpg",
                "https://s7.picofile.com/file/8375511376/p18_5.jpg",
                "https://s7.picofile.com/file/8382167868/p18_7.jpg"
            ),  // 17
            arrayOf(
                "https://s6.picofile.com/file/8375511384/p19_1.jpg",
                "https://s6.picofile.com/file/8375511392/p19_2.jpg",
                "https://s6.picofile.com/file/8375511418/p19_3.jpg",
                "https://s6.picofile.com/file/8375511426/p19_4.jpg",
                "https://s7.picofile.com/file/8375511434/p19_5.jpg"
            ),  //18
            arrayOf(
                "https://s7.picofile.com/file/8375511442/p20_1.jpg",
                "https://s7.picofile.com/file/8375511450/p20_2.jpg",
                "https://s6.picofile.com/file/8375511468/p20_3.jpg"
            ),  //19
            arrayOf(

                "https://s6.picofile.com/file/8375511476/p21_1.jpg",
                "https://s6.picofile.com/file/8375511484/p21_2.jpg",
                "https://s6.picofile.com/file/8375511492/p21_3.jpg",
                "https://s6.picofile.com/file/8375511518/p21_4.jpg",
                "https://s6.picofile.com/file/8375511534/p21_5.jpg"
            ),  // 20
            arrayOf(

                "https://s7.picofile.com/file/8375511542/p22_1.jpg",
                "https://s7.picofile.com/file/8375511576/p22_2.jpg",
                "https://s6.picofile.com/file/8375511584/p22_3.jpg",
                "https://s6.picofile.com/file/8375511592/p22_4.jpg",
                "https://s6.picofile.com/file/8375511600/p22_5.jpg",
                "https://s7.picofile.com/file/8382167918/p22_6.jpg"
            ),  //21
            arrayOf(
                "https://s7.picofile.com/file/8375511626/p23_1.jpg",
                "https://s6.picofile.com/file/8375511634/p23_2.jpg",
                "https://s6.picofile.com/file/8375511642/p23_3.jpg",
                "https://s7.picofile.com/file/8375511668/p23_4.jpg",
                "https://s6.picofile.com/file/8375511684/p23_5.jpg"
            ),  //22
            arrayOf(

                "https://s7.picofile.com/file/8375511692/p24_1.jpg",
                "https://s6.picofile.com/file/8375511718/p24_2.jpg",
                "https://s7.picofile.com/file/8375511734/p24_3.jpg",
                "https://s7.picofile.com/file/8375511742/p24_4.jpg",
                "https://s6.picofile.com/file/8375511750/p24_5.jpg"
            ),  // 23
            arrayOf(

                "https://s7.picofile.com/file/8375511768/p25_1.jpg",
                "https://s7.picofile.com/file/8375511776/p25_2.jpg",
                "https://s7.picofile.com/file/8375511784/p25_3.jpg",
                "https://s6.picofile.com/file/8375511792/p25_4.jpg",
                "https://s6.picofile.com/file/8375511800/p25_5.jpg",
                "https://s6.picofile.com/file/8382167934/p25_6.jpg",
                "https://s6.picofile.com/file/8382167976/p25_7.jpg",
                "https://s7.picofile.com/file/8382168018/p25_8.jpg",
                "https://s6.picofile.com/file/8382168034/p25_9.jpg"
            ),  //24
            arrayOf(
                "https://s6.picofile.com/file/8375511818/p26_1.jpg",
                "https://s6.picofile.com/file/8375511834/p26_2.jpg",
                "https://s7.picofile.com/file/8375511850/p26_3.jpg",
                "https://s7.picofile.com/file/8375511868/p26_4.jpg",
                "https://s7.picofile.com/file/8375511876/p26_5.jpg"
            ),  // 25
            arrayOf(

                "https://s7.picofile.com/file/8375511884/p27_1.jpg",
                "https://s6.picofile.com/file/8375511892/p27_2.jpg",
                "https://s6.picofile.com/file/8375511900/p27_3.jpg",
                "https://s6.picofile.com/file/8375511918/p27_4.jpg",
                "https://s7.picofile.com/file/8375511926/p27_5.jpg"
            ),  // 26
            arrayOf(

                "https://s6.picofile.com/file/8375511934/p28_1.jpg",
                "https://s7.picofile.com/file/8375511950/p28_2.jpg",
                "https://s7.picofile.com/file/8375511976/p28_3.jpg",
                "https://s6.picofile.com/file/8375511984/p28_4.jpg",
                "https://s6.picofile.com/file/8375512000/p28_5.jpg"
            ),  //27
            arrayOf(

                "https://s6.picofile.com/file/8375512018/p29_1.jpg",
                "https://s7.picofile.com/file/8375512026/p29_2.jpg",
                "https://s7.picofile.com/file/8375512034/p29_3.jpg",
                "https://s6.picofile.com/file/8375512042/p29_4.jpg",
                "https://s7.picofile.com/file/8375512050/p29_5.jpg",
                "https://s7.picofile.com/file/8382168076/p29_6.jpg"
            ),  //28
            arrayOf(

                "https://s7.picofile.com/file/8375512068/p30_1.jpg",
                "https://s7.picofile.com/file/8375512076/p30_2.jpg",
                "https://s7.picofile.com/file/8375512084/p30_3.jpg",
                "https://s7.picofile.com/file/8375512092/p30_4.jpg",
                "https://s6.picofile.com/file/8375512100/p30_5.jpg"
            ),  // 29
            arrayOf(
                "https://s7.picofile.com/file/8375964884/p31_1.jpg"
            ),  // 30
            arrayOf(

                "https://s7.picofile.com/file/8375512126/p32_1.jpg",
                "https://s7.picofile.com/file/8375512134/p32_2.jpg",
                "https://s7.picofile.com/file/8375512150/p32_3.jpg",
                "https://s7.picofile.com/file/8375512168/p32_4.jpg",
                "https://s7.picofile.com/file/8375512184/p32_5.jpg"
            ),  // 31
            arrayOf(

                "https://s6.picofile.com/file/8375512192/p33_1.jpg",
                "https://s6.picofile.com/file/8375512200/p33_2.jpg",
                "https://s7.picofile.com/file/8375512218/p33_3.jpg",
                "https://s7.picofile.com/file/8375512226/p33_4.jpg",
                "https://s6.picofile.com/file/8375512234/p33_5.jpg"
            ),  // 32
            arrayOf(
                "https://s7.picofile.com/file/8375512242/p34_1.jpg",
                "https://s7.picofile.com/file/8375512250/p34_2.jpg",
                "https://s6.picofile.com/file/8375512268/p34_3.jpg",
                "https://s7.picofile.com/file/8375512284/p34_4.jpg",
                "https://s6.picofile.com/file/8375512292/p34_5.jpg",
            ),  //33
            arrayOf(

                "https://s7.picofile.com/file/8375512300/p35_1.jpg",
                "https://s6.picofile.com/file/8375512318/p35_2.jpg",
                "https://s7.picofile.com/file/8375512326/p35_3.jpg",
                "https://s6.picofile.com/file/8375512334/p35_4.jpg",
                "https://s6.picofile.com/file/8375512350/p35_5.jpg"
            ),  //34
            arrayOf(

                "https://s7.picofile.com/file/8375512368/p36_1.jpg",
                "https://s6.picofile.com/file/8375512376/p36_2.jpg",
                "https://s7.picofile.com/file/8375512384/p36_3.jpg",
                "https://s7.picofile.com/file/8375512392/p36_4.jpg",
                "https://s6.picofile.com/file/8375512400/p36_5.jpg"
            ),  //35
            arrayOf(

                "https://s6.picofile.com/file/8375512418/p37_1.jpg",
                "https://s7.picofile.com/file/8375512426/p37_2.jpg",
                "https://s6.picofile.com/file/8375512434/p37_3.jpg",
                "https://s7.picofile.com/file/8375512442/p37_4.jpg",
                "https://s6.picofile.com/file/8375512450/p37_5.jpg",
                "https://s7.picofile.com/file/8382168118/p37_6.jpg"
            ),  //36
            arrayOf(
                "https://s7.picofile.com/file/8375512468/p38_1.jpg",
                "https://s6.picofile.com/file/8375512484/p38_2.jpg",
                "https://s7.picofile.com/file/8375512492/p38_3.jpg",
                "https://s7.picofile.com/file/8375512500/p38_4.jpg",
                "https://s7.picofile.com/file/8375512518/p38_5.jpg"
            ),  //37
            arrayOf(

                "https://s6.picofile.com/file/8375512526/p39_1.jpg",
                "https://s6.picofile.com/file/8375512534/p39_2.jpg",
                "https://s6.picofile.com/file/8375512542/p39_3.jpg",
                "https://s7.picofile.com/file/8375512550/p39_4.jpg",
                "https://s6.picofile.com/file/8375512568/p39_5.jpg",
                "https://s7.picofile.com/file/8382168126/p39_6.jpg"
            ),  // 38
            arrayOf(

                "https://s6.picofile.com/file/8375512576/p40_1.jpg",
                "https://s7.picofile.com/file/8375512584/p40_2.jpg",
                "https://s6.picofile.com/file/8375512592/p40_3.jpg",
                "https://s6.picofile.com/file/8375512618/p40_4.jpg",
                "https://s6.picofile.com/file/8375512626/p40_5.jpg",
                "https://s7.picofile.com/file/8382168200/p40_6.jpg"
            ),  // 39
            arrayOf(
                "https://s6.picofile.com/file/8375512634/p41_1.jpg",
                "https://s6.picofile.com/file/8375512642/p41_2.jpg",
                "https://s7.picofile.com/file/8375512650/p41_3.jpg",
                "https://s6.picofile.com/file/8375512668/p41_4.jpg",
                "https://s7.picofile.com/file/8375512676/p41_5.jpg",
                "https://s7.picofile.com/file/8382168276/p41_6.jpg"
            ),  // 40
            arrayOf(

                "https://s7.picofile.com/file/8375512692/p42_1.jpg",
                "https://s6.picofile.com/file/8375512718/p42_2.jpg",
                "https://s7.picofile.com/file/8375512726/p42_3.jpg",
                "https://s6.picofile.com/file/8375512734/p42_4.jpg",
                "https://s7.picofile.com/file/8375512742/p42_5.jpg"
            ),  // 41
            arrayOf(
                "https://s6.picofile.com/file/8375512750/p43_1.jpg",
                "https://s6.picofile.com/file/8375512768/p43_2.jpg",
                "https://s7.picofile.com/file/8375512776/p43_3.jpg",
                "https://s7.picofile.com/file/8375512784/p43_4.jpg",
                "https://s6.picofile.com/file/8375512792/p43_5.jpg"
            ),  //42
            arrayOf(

                "https://s7.picofile.com/file/8375512818/p44_1.jpg",
                "https://s6.picofile.com/file/8375512826/p44_2.jpg",
                "https://s6.picofile.com/file/8375512834/p44_3.jpg",
                "https://s7.picofile.com/file/8375512842/p44_4.jpg",
                "https://s6.picofile.com/file/8375512850/p44_5.jpg"
            ),  //43
            arrayOf(
                "https://s6.picofile.com/file/8375512868/p45_1.jpg",
                "https://s6.picofile.com/file/8375512884/p45_2.jpg",
                "https://s7.picofile.com/file/8375512892/p45_3.jpg",
                "https://s7.picofile.com/file/8375512900/p45_4.jpg",
                "https://s6.picofile.com/file/8375512918/p45_5.jpg"
            ),  //44
            arrayOf(

                "https://s6.picofile.com/file/8375512926/p46_1.jpg",
                "https://s7.picofile.com/file/8375512942/p46_2.jpg",
                "https://s6.picofile.com/file/8375512976/p46_4.jpg",
                "https://s6.picofile.com/file/8375512984/p46_5.jpg"
            ),  //45
            arrayOf(

                "https://s7.picofile.com/file/8375512992/p47_1.jpg",
                "https://s6.picofile.com/file/8375513000/p47_2.jpg",
                "https://s7.picofile.com/file/8375513018/p47_3.jpg",
                "https://s6.picofile.com/file/8375513026/p47_4.jpg",
                "https://s7.picofile.com/file/8375513034/p47_5.jpg"
            ),  //46
            arrayOf(
                "https://s6.picofile.com/file/8375513042/p48_1.jpg",
                "https://s7.picofile.com/file/8375513050/p48_2.jpg",
                "https://s6.picofile.com/file/8375513068/p48_3.jpg",
                "https://s6.picofile.com/file/8375513076/p48_4.jpg",
                "https://s7.picofile.com/file/8375513084/p48_5.jpg"
            ),  // 47
            arrayOf(

                "https://s6.picofile.com/file/8375513092/p49_1.jpg",
                "https://s6.picofile.com/file/8375513100/p49_2.jpg",
                "https://s6.picofile.com/file/8375513118/p49_3.jpg",
                "https://s7.picofile.com/file/8375513126/p49_4.jpg",
                "https://s7.picofile.com/file/8375513134/p49_5.jpg",
                "https://s6.picofile.com/file/8382168300/p49_6.jpg",
                "https://s7.picofile.com/file/8382168350/p49_7.jpg"
            ),  // 48
            arrayOf(

                "https://s6.picofile.com/file/8375513142/p50_1.jpg",
                "https://s6.picofile.com/file/8375513150/p50_2.jpg",
                "https://s6.picofile.com/file/8375513168/p50_3.jpg",
                "https://s6.picofile.com/file/8375513176/p50_4.jpg",
                "https://s6.picofile.com/file/8375513184/p50_5.jpg"
            ),  // 49
            arrayOf(
                "https://s7.picofile.com/file/8375513200/p51_1.jpg",
                "https://s7.picofile.com/file/8375513218/p51_2.jpg",
                "https://s6.picofile.com/file/8375513226/p51_3.jpg",
                "https://s6.picofile.com/file/8375513234/p51_4.jpg",
                "https://s6.picofile.com/file/8375513242/p51_5.jpg"
            ),  // 50
            arrayOf(

                "https://s7.picofile.com/file/8375513250/p52_1.jpg",
                "https://s7.picofile.com/file/8375513268/p52_2.jpg",
                "https://s6.picofile.com/file/8375513276/p52_3.jpg",
                "https://s6.picofile.com/file/8375513284/p52_4.jpg",
                "https://s6.picofile.com/file/8375513292/p52_5.jpg"
            ),  //51
            arrayOf(
                "https://s6.picofile.com/file/8375513318/p53_1.jpg",
                "https://s7.picofile.com/file/8375513326/p53_2.jpg",
                "https://s7.picofile.com/file/8375513334/p53_3.jpg",
                "https://s6.picofile.com/file/8375513342/p53_4.jpg",
                "https://s6.picofile.com/file/8375513350/p53_5.jpg"
            ),  // 52
            arrayOf(
                "https://s6.picofile.com/file/8375513368/p54_1.jpg",
                "https://s6.picofile.com/file/8375513376/p54_2.jpg",
                "https://s7.picofile.com/file/8375513384/p54_3.jpg",
                "https://s6.picofile.com/file/8375513392/p54_4.jpg",
                "https://s6.picofile.com/file/8375513400/p54_5.jpg"
            ),  // 53
            arrayOf(

                "https://s7.picofile.com/file/8375654776/p55_1.jpg",
                "https://s6.picofile.com/file/8375654792/p55_2.jpg",
                "https://s6.picofile.com/file/8375654818/p55_3.jpg",
                "https://s6.picofile.com/file/8375654826/p55_4.jpg",
                "https://s6.picofile.com/file/8375654834/p55_5.jpg"
            ),  // 54
            arrayOf(

                "https://s6.picofile.com/file/8375513418/p56_1.jpg",
                "https://s7.picofile.com/file/8375513426/p56_2.jpg",
                "https://s7.picofile.com/file/8375513434/p56_3.jpg",
                "https://s7.picofile.com/file/8375513442/p56_4.jpg",
                "https://s7.picofile.com/file/8375513468/p56_5.jpg",
                "https://s7.picofile.com/file/8382168418/p56_6.jpg",
                "https://s7.picofile.com/file/8382168468/p56_7.jpg",
                "https://s6.picofile.com/file/8382168484/p56_8.jpg"
            ),  // 55
            arrayOf(

                "https://s6.picofile.com/file/8375513476/p57_1.jpg",
                "https://s7.picofile.com/file/8375513484/p57_2.jpg",
                "https://s6.picofile.com/file/8375513492/p57_3.jpg",
                "https://s6.picofile.com/file/8375513500/p57_4.jpg",
                "https://s7.picofile.com/file/8375513526/p57_5.jpg"
            ),  // 56
            arrayOf(

                "https://s6.picofile.com/file/8375513534/p58_1.jpg",
                "https://s6.picofile.com/file/8375513542/p58_2.jpg",
                "https://s7.picofile.com/file/8375513550/p58_3.jpg",
                "https://s6.picofile.com/file/8375513568/p58_4.jpg",
                "https://s7.picofile.com/file/8375513584/p58_5.jpg"
            ),  //57
            arrayOf(
                "https://s6.picofile.com/file/8375513592/p59_1.jpg",
                "https://s6.picofile.com/file/8375513600/p59_2.jpg",
                "https://s6.picofile.com/file/8375513618/p59_3.jpg",
                "https://s6.picofile.com/file/8375513626/p59_4.jpg",
                "https://s6.picofile.com/file/8375513634/p59_5.jpg"
            ),  //58
            arrayOf(

                "https://s6.picofile.com/file/8375513642/p60_1.jpg",
                "https://s7.picofile.com/file/8375513650/p60_2.jpg",
                "https://s6.picofile.com/file/8375513668/p60_3.jpg",
                "https://s7.picofile.com/file/8375513676/p60_4.jpg",
                "https://s7.picofile.com/file/8375513684/p60_5.jpg"
            ),  // 59
            arrayOf(
                "https://s7.picofile.com/file/8375513692/p61_1.jpg",
                "https://s6.picofile.com/file/8375513700/p61_2.jpg",
                "https://s6.picofile.com/file/8375513718/p61_3.jpg",
                "https://s7.picofile.com/file/8375513726/p61_4.jpg",
                "https://s6.picofile.com/file/8375513734/p61_5.jpg"
            ),  // 60
            arrayOf(

                "https://s6.picofile.com/file/8375513742/p62_1.jpg",
                "https://s6.picofile.com/file/8375513750/p62_2.jpg",
                "https://s7.picofile.com/file/8375513776/p62_3.jpg",
                "https://s6.picofile.com/file/8375513792/p62_4.jpg",
                "https://s6.picofile.com/file/8375513800/p62_5.jpg"
            ),  //61
            arrayOf(

                "https://s7.picofile.com/file/8375513818/p63_1.jpg",
                "https://s6.picofile.com/file/8375513826/p63_2.jpg",
                "https://s7.picofile.com/file/8375513842/p63_3.jpg",
                "https://s7.picofile.com/file/8375513850/p63_4.jpg",
                "https://s7.picofile.com/file/8375513868/p63_5.jpg"
            ),  //62
            arrayOf(

                "https://s7.picofile.com/file/8375513876/p64_1.jpg",
                "https://s7.picofile.com/file/8375513884/p64_2.jpg",
                "https://s6.picofile.com/file/8375513900/p64_3.jpg",
                "https://s6.picofile.com/file/8375513918/p64_4.jpg",
                "https://s7.picofile.com/file/8375513926/p64_5.jpg",
                "https://s6.picofile.com/file/8382168684/p64_6.jpg",
                "https://s7.picofile.com/file/8387341242/p64_7.jpg"
            ),  // 63
            arrayOf(
                "https://s7.picofile.com/file/8375513934/p65_1.jpg",
                "https://s7.picofile.com/file/8375513942/p65_2.jpg",
                "https://s7.picofile.com/file/8375513950/p65_3.jpg",
                "https://s6.picofile.com/file/8375513968/p65_4.jpg",
                "https://s7.picofile.com/file/8375513976/p65_5.jpg"
            ),  // 64
            arrayOf(

                "https://s7.picofile.com/file/8375513984/p66_1.jpg",
                "https://s7.picofile.com/file/8375513992/p66_2.jpg",
                "https://s6.picofile.com/file/8375514000/p66_3.jpg",
                "https://s7.picofile.com/file/8375514018/p66_4.jpg",
                "https://s7.picofile.com/file/8375514026/p66_5.jpg"
            ),  // 65
            arrayOf(

                "https://s6.picofile.com/file/8375514034/p67_1.jpg",
                "https://s7.picofile.com/file/8375514042/p67_2.jpg",
                "https://s7.picofile.com/file/8375514050/p67_3.jpg",
                "https://s6.picofile.com/file/8375514068/p67_4.jpg",
                "https://s7.picofile.com/file/8375514076/p67_5.jpg"
            ),  // 66
            arrayOf(

                "https://s7.picofile.com/file/8375514084/p68_1.jpg",
                "https://s6.picofile.com/file/8375514092/p68_2.jpg",
                "https://s6.picofile.com/file/8375514100/p68_3.jpg",
                "https://s7.picofile.com/file/8375514126/p68_4.jpg",
                "https://s7.picofile.com/file/8375514134/p68_5.jpg"
            ),  //67
            arrayOf(

                "https://s7.picofile.com/file/8375514142/p69_1.jpg",
                "https://s7.picofile.com/file/8375514150/p69_2.jpg",
                "https://s6.picofile.com/file/8375514168/p69_3.jpg",
                "https://s7.picofile.com/file/8375514176/p69_4.jpg",
                "https://s7.picofile.com/file/8375514184/p69_5.jpg"
            ),  //68
            arrayOf(

                "https://s6.picofile.com/file/8375514192/p70_1.jpg",
                "https://s7.picofile.com/file/8375514200/p70_2.jpg",
                "https://s7.picofile.com/file/8375514218/p70_3.jpg",
                "https://s7.picofile.com/file/8375514226/p70_4.jpg",
                "https://s7.picofile.com/file/8375514234/p70_5.jpg"
            ),  //69
            arrayOf(

                "https://s7.picofile.com/file/8375514242/p71_1.jpg",
                "https://s6.picofile.com/file/8375514250/p71_2.jpg",
                "https://s7.picofile.com/file/8375514276/p71_3.jpg",
                "https://s6.picofile.com/file/8375514292/p71_4.jpg",
                "https://s6.picofile.com/file/8375514300/p71_5.jpg"
            ),  //70
            arrayOf(
                "https://s6.picofile.com/file/8375514318/p72_1.jpg",
                "https://s7.picofile.com/file/8375514326/p72_2.jpg",
                "https://s6.picofile.com/file/8375514334/p72_3.jpg",
                "https://s7.picofile.com/file/8375514342/p72_4.jpg",
                "https://s7.picofile.com/file/8375514350/p72_5.jpg"
            ),  //71
            arrayOf(

                "https://s6.picofile.com/file/8375514368/p73_1.jpg",
                "https://s6.picofile.com/file/8375514376/p73_2.jpg",
                "https://s7.picofile.com/file/8375514392/p73_3.jpg",
                "https://s7.picofile.com/file/8375514400/p73_4.jpg",
                "https://s6.picofile.com/file/8375514418/p73_5.jpg"
            ),  // 72
            arrayOf(

                "https://s7.picofile.com/file/8375514426/p74_1.jpg",
                "https://s7.picofile.com/file/8375514434/p74_2.jpg",
                "https://s6.picofile.com/file/8375514442/p74_3.jpg",
                "https://s6.picofile.com/file/8375514450/p74_4.jpg",
                "https://s6.picofile.com/file/8375514476/p74_5.jpg"
            ),  // 73
            arrayOf(
                "https://s6.picofile.com/file/8375514492/p75_1.jpg",
                "https://s7.picofile.com/file/8375514500/p75_2.jpg",
                "https://s7.picofile.com/file/8375514518/p75_3.jpg",
                "https://s6.picofile.com/file/8375514526/p75_4.jpg",
                "https://s6.picofile.com/file/8375514542/p75_5.jpg"
            ) // 74
        ))

    }
}